package rocks.coffeenet.autoconfigure.security.oauth2;

/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public interface OAuth2TextFixtures {

    String[] OAUTH_GITHUB_PROPERTIES = new String[] {
        "spring.security.oauth2.client.registration.github.client-id=example-client-id",
        "spring.security.oauth2.client.registration.github.client-secret=example-client-secret"
    };
}
