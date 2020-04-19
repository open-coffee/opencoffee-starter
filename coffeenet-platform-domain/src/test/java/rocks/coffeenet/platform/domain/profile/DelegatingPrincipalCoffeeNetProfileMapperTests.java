package rocks.coffeenet.platform.domain.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.Principal;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName("DelegatingPrincipalCoffeeNetProfileMapper")
class DelegatingPrincipalCoffeeNetProfileMapperTests {

    @Test
    @DisplayName("errors on null valued profile mappers")
    void errorOnNullMappers() {

        assertThat(catchThrowable(() -> new DelegatingPrincipalCoffeeNetProfileMapper(null))).isInstanceOf(
            IllegalArgumentException.class);
    }


    @Test
    @DisplayName("errors on empty profile mappers")
    void errorOnEmptyMappers() {

        assertThat(catchThrowable(() -> new DelegatingPrincipalCoffeeNetProfileMapper(Collections.emptyList())))
            .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("supports principal types on delegated mappers")
    void supportsPrincipalTypes() {

        // Given/When
        DelegatingPrincipalCoffeeNetProfileMapper mapper = new DelegatingPrincipalCoffeeNetProfileMapper(Arrays.asList(
                    new ExamplePrincipalCoffeeNetProfileMapper(),
                    new DifferentExamplePrincipalCoffeeNetProfileMapper()));

        // Then
        assertThat(mapper.supports(TestPrincipals.ExamplePrincipal.class)).isTrue();
        assertThat(mapper.supports(TestPrincipals.DifferentExamplePrincipal.class)).isTrue();
    }


    @Test
    @DisplayName("reports no support if no matching principal types")
    void noSupportedPrincipalTypes() {

        // Given/When
        DelegatingPrincipalCoffeeNetProfileMapper mapper = new DelegatingPrincipalCoffeeNetProfileMapper(Arrays.asList(
                    new DifferentExamplePrincipalCoffeeNetProfileMapper()));

        // Then
        assertThat(mapper.supports(TestPrincipals.ExamplePrincipal.class)).isFalse();
    }


    @Test
    @DisplayName("returns null on no supported principal types")
    void returnNullNoSupportedPrincipalTypes() {

        // Given/When
        DelegatingPrincipalCoffeeNetProfileMapper mapper = new DelegatingPrincipalCoffeeNetProfileMapper(Arrays.asList(
                    new DifferentExamplePrincipalCoffeeNetProfileMapper()));

        // When
        TestPrincipals.ExamplePrincipal example = new TestPrincipals.ExamplePrincipal("example");

        // Then
        assertThat(mapper.map(example)).isNull();
    }


    @Test
    @DisplayName("maps correctly on matching delegate mapper")
    void matchingDelegateMapper() {

        // Given
        DelegatingPrincipalCoffeeNetProfileMapper mapper = new DelegatingPrincipalCoffeeNetProfileMapper(Arrays.asList(
                    new ExamplePrincipalCoffeeNetProfileMapper(),
                    new DifferentExamplePrincipalCoffeeNetProfileMapper()));

        // When
        TestPrincipals.DifferentExamplePrincipal example = new TestPrincipals.DifferentExamplePrincipal("example");

        // Then
        assertThat(mapper.map(example)).isNotNull().extracting(CoffeeNetProfile::getName).isEqualTo("example");
    }


    @Test
    @DisplayName("maps to null on no matching mapper")
    void noMatchingDelegateMapper() {

        // Given
        DelegatingPrincipalCoffeeNetProfileMapper mapper = new DelegatingPrincipalCoffeeNetProfileMapper(Arrays.asList(
                    new ExamplePrincipalCoffeeNetProfileMapper()));

        // When
        TestPrincipals.DifferentExamplePrincipal example = new TestPrincipals.DifferentExamplePrincipal("example");

        // Then
        assertThat(mapper.map(example)).isNull();
    }

    static class ExamplePrincipalCoffeeNetProfileMapper implements PrincipalCoffeeNetProfileMapper {

        @Override
        public CoffeeNetProfile map(Principal principal) {

            TestPrincipals.ExamplePrincipal p = (TestPrincipals.ExamplePrincipal) principal;

            return CoffeeNetProfile.withUniqueIdentifierAndName(p.getName(), p.getName()).build();
        }


        @Override
        public boolean supports(Class<? extends Principal> clazz) {

            return TestPrincipals.ExamplePrincipal.class.isAssignableFrom(clazz);
        }
    }

    static class DifferentExamplePrincipalCoffeeNetProfileMapper implements PrincipalCoffeeNetProfileMapper {

        @Override
        public CoffeeNetProfile map(Principal principal) {

            TestPrincipals.DifferentExamplePrincipal p = (TestPrincipals.DifferentExamplePrincipal) principal;

            return CoffeeNetProfile.withUniqueIdentifierAndName(p.getName(), p.getName()).build();
        }


        @Override
        public boolean supports(Class<? extends Principal> clazz) {

            return TestPrincipals.DifferentExamplePrincipal.class.isAssignableFrom(clazz);
        }
    }
}
