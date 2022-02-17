package rocks.coffeenet.autoconfigure.security.oauth2.helpers;

import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;

import java.time.Duration;
import java.time.Instant;

import java.util.HashMap;
import java.util.Map;


/**
 * Builder for OidcIdToken instances for tests.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class OidcIdTokenBuilder {

    public static String TOKEN_VALUE = "test-token-value";
    public static String DEFAULT_AUDIENCE = "tests";

    private Map<String, Object> claims = new HashMap<>();
    private Instant iat = Instant.now();
    private Instant exp = Instant.now().plus(Duration.ofMinutes(5));

    OidcIdTokenBuilder(String issuer, String sub) {

        claims.put(IdTokenClaimNames.ISS, issuer);
        claims.put(IdTokenClaimNames.SUB, sub);
        claims.put(IdTokenClaimNames.AUD, DEFAULT_AUDIENCE);
        claims.put(IdTokenClaimNames.IAT, iat);
        claims.put(IdTokenClaimNames.EXP, exp);
    }

    public OidcIdTokenBuilder withClaim(String claimName, Object value) {

        claims.put(claimName, value);

        return this;
    }


    public OidcIdTokenBuilder withClaims(Map<String, Object> claims) {

        claims.putAll(claims);

        return this;
    }


    public OidcIdToken build() {

        return new OidcIdToken(TOKEN_VALUE, iat, exp, claims);
    }


    public static OidcIdTokenBuilder withIssuerAndSub(String issuer, String sub) {

        return new OidcIdTokenBuilder(issuer, sub);
    }
}
