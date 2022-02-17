package rocks.coffeenet.platform.domain.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName("PrincipalCoffeeNetProfileMapper")
class PrincipalCoffeeNetProfileMapperTests {

    @Test
    @DisplayName("supports Principal class if not implemented otherwise")
    void supportPrincipalByDefault() {

        // Given/When
        ExamplePrincipalProfileMapper mapper = new ExamplePrincipalProfileMapper();

        // Then
        assertThat(mapper.supports(Principal.class)).isTrue();
    }

    static class ExamplePrincipalProfileMapper implements PrincipalCoffeeNetProfileMapper {

        @Override
        public CoffeeNetProfile map(Principal principal) {

            return CoffeeNetProfile.withUniqueIdentifierAndName("test", "test").build();
        }
    }
}
