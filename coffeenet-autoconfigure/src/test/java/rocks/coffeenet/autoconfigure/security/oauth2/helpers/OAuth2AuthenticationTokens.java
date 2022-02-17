package rocks.coffeenet.autoconfigure.security.oauth2.helpers;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collections;
import java.util.Set;


/**
 * Helpers to generate {@link OAuth2AuthenticationToken} instances for tests.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public interface OAuth2AuthenticationTokens {

    String DEFAULT_CLIENT_REGISTRATION_ID = "example";
    SimpleGrantedAuthority DEFAULT_GRANTED_AUTHORITY = new SimpleGrantedAuthority("ROLE_USER");

    String DEFAULT_PROFILE_URL = "http://example.com/~user";
    String DEFAULT_PICTURE_URL = "http://example.com/~user";

    static OAuth2AuthenticationToken withIssuerAndSub(String issuer, String sub) {

        Set<SimpleGrantedAuthority> authorities = Collections.singleton(DEFAULT_GRANTED_AUTHORITY);
        OidcIdToken idToken = OidcIdTokenBuilder.withIssuerAndSub(issuer, sub)
                .withClaim(StandardClaimNames.PROFILE, DEFAULT_PROFILE_URL)
                .withClaim(StandardClaimNames.PICTURE, DEFAULT_PICTURE_URL)
                .build();

        OidcUser principal = new DefaultOidcUser(authorities, idToken);

        return new OAuth2AuthenticationToken(principal, authorities, DEFAULT_CLIENT_REGISTRATION_ID);
    }
}
