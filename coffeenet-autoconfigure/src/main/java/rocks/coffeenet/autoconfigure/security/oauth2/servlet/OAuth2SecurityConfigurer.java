package rocks.coffeenet.autoconfigure.security.oauth2.servlet;

import org.springframework.boot.autoconfigure.security.oauth2.client.ClientsConfiguredCondition;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import rocks.coffeenet.autoconfigure.security.servlet.CoffeeNetSecurityConfigurer;


/**
 * Enables OAuth2/OIDC for all {@link WebSecurityConfigurerAdapter} instances. Automatically applied, when
 * {@link ClientsConfiguredCondition} is matching.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
public class OAuth2SecurityConfigurer extends AbstractHttpConfigurer<OAuth2SecurityConfigurer, HttpSecurity>
    implements CoffeeNetSecurityConfigurer {

    @Override
    public void init(HttpSecurity http) throws Exception {

        http.oauth2Login()
            .and()
            .oauth2Client();
    }
}
