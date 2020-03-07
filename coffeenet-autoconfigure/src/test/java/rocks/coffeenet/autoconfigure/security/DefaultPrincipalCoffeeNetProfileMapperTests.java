package rocks.coffeenet.autoconfigure.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.security.authentication.TestingAuthenticationToken;

import rocks.coffeenet.platform.domain.profile.CoffeeNetProfile;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName("DefaultPrincipalCoffeeNetProfileMapper")
class DefaultPrincipalCoffeeNetProfileMapperTests {

    @Test
    @DisplayName("should map null from empty principal")
    void emptyHandling() {

        DefaultPrincipalCoffeeNetProfileMapper mapper = new DefaultPrincipalCoffeeNetProfileMapper();
        TestingAuthenticationToken principal = new TestingAuthenticationToken("", "credentials");
        assertThat(mapper.map(principal))
            .isNull();
    }


    @Test
    @DisplayName("should map null from null principal")
    void nullHandling() {

        DefaultPrincipalCoffeeNetProfileMapper mapper = new DefaultPrincipalCoffeeNetProfileMapper();
        assertThat(mapper.map(null)).isNull();
    }


    @Test
    @DisplayName("should map a profile from any principal with a non-null name")
    void mapSimplePrincipal() {

        DefaultPrincipalCoffeeNetProfileMapper mapper = new DefaultPrincipalCoffeeNetProfileMapper();
        TestingAuthenticationToken principal = new TestingAuthenticationToken("example-user", "credentials");

        assertThat(mapper.map(principal))
            .isNotNull()
            .isInstanceOf(CoffeeNetProfile.class)
            .satisfies(profile -> {
                assertThat(profile.getUniqueIdentifier()).isNotNull();
                assertThat(profile.getName()).isEqualTo("example-user");
                assertThat(profile.getHumanReadableName()).isEqualTo("example-user");
            });
    }
}
