package rocks.coffeenet.autoconfigure.security.oauth2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import rocks.coffeenet.autoconfigure.security.oauth2.helpers.OAuth2AuthenticationTokens;

import rocks.coffeenet.platform.domain.profile.CoffeeNetProfile;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName(" OAuth2AuthenticationTokenProfileMapper")
class OAuth2AuthenticationTokenProfileMapperTests {

    @Test
    @DisplayName("should map a profile from a fully populated OAuth2 authentication")
    void mapOAuth2Authentication() {

        // TODO: Write some test code to obtain a OAuth2TokenAuthentication
        OAuth2AuthenticationTokenProfileMapper mapper = new OAuth2AuthenticationTokenProfileMapper();
        OAuth2AuthenticationToken token = OAuth2AuthenticationTokens.withIssuerAndSub("test", "user");
        assertThat(mapper.map(token)).isInstanceOf(CoffeeNetProfile.class).satisfies(t -> {
            assertThat(t.getUniqueIdentifier()).isNotNull();
            assertThat(t.getName()).isEqualTo("user");
        });
    }


    @Test
    @DisplayName("should support OAuth2 principals")
    void supportOAuth2TokenAuthentication() {

        OAuth2AuthenticationTokenProfileMapper mapper = new OAuth2AuthenticationTokenProfileMapper();
        assertThat(mapper.supports(OAuth2AuthenticationToken.class)).isTrue();
    }


    @Test
    @DisplayName("should not support general authentication principals")
    void noGeneralPrincipalSupport() {

        OAuth2AuthenticationTokenProfileMapper mapper = new OAuth2AuthenticationTokenProfileMapper();
        assertThat(mapper.supports(Principal.class)).isFalse();
    }
}
