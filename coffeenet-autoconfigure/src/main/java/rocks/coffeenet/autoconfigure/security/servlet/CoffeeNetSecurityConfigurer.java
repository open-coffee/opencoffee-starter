package rocks.coffeenet.autoconfigure.security.servlet;

import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;


/**
 * A marker interface for {@link SecurityConfigurer} instances, that will be automatically applied by
 * {@link DelegatingSecurityConfigurer}. They are used in the CoffeeNet application platform, to enable security
 * features in auto-configuration.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
public interface CoffeeNetSecurityConfigurer extends SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {
}
