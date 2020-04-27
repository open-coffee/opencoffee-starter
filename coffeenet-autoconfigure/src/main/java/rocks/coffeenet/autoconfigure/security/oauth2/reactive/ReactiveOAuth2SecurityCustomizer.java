package rocks.coffeenet.autoconfigure.security.oauth2.reactive;

import org.springframework.boot.autoconfigure.security.oauth2.client.ClientsConfiguredCondition;

import org.springframework.security.config.web.server.ServerHttpSecurity;

import rocks.coffeenet.autoconfigure.security.reactive.ReactiveServerHttpSecurityCustomizer;


/**
 * Enables OAuth2/OIDC for all {@link ServerHttpSecurity} instances. Automatically applied, when
 * {@link ClientsConfiguredCondition} is matching.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class ReactiveOAuth2SecurityCustomizer implements ReactiveServerHttpSecurityCustomizer {

    @Override
    public void customize(ServerHttpSecurity http) {

        http.oauth2Client()
            .and()
            .oauth2Login();
    }
}
