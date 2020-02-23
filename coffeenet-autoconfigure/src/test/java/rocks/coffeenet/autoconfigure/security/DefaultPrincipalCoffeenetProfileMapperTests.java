package rocks.coffeenet.autoconfigure.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.security.authentication.TestingAuthenticationToken;

import rocks.coffeenet.platform.domain.profile.CoffeeNetProfile;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName("DefaultPrincipalCoffeenetProfileMapper")
class DefaultPrincipalCoffeenetProfileMapperTests {

    @Test
    @DisplayName("should map null from empty principal")
    void emptyHandling() {

        DefaultPrincipalCoffeenetProfileMapper mapper = new DefaultPrincipalCoffeenetProfileMapper();
        TestingAuthenticationToken principal = new TestingAuthenticationToken("", "credentials");
        assertThat(mapper.map(principal))
            .isNull();
    }


    @Test
    @DisplayName("should map null from null principal")
    void nullHandling() {

        DefaultPrincipalCoffeenetProfileMapper mapper = new DefaultPrincipalCoffeenetProfileMapper();
        assertThat(mapper.map(null)).isNull();
    }


    @Test
    @DisplayName("should map a profile from any principal with a non-null name")
    void mapSimplePrincipal() {

        DefaultPrincipalCoffeenetProfileMapper mapper = new DefaultPrincipalCoffeenetProfileMapper();
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
