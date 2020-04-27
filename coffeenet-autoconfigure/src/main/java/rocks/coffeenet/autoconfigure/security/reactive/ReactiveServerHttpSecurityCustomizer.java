package rocks.coffeenet.autoconfigure.security.reactive;

import org.springframework.security.config.web.server.ServerHttpSecurity;


/**
 * A functional interface to customize {@link ServerHttpSecurity} instances. Instances of this interface will be
 * automatically applied by {@link ReactiveServerHttpSecurityPostProcessor}. They are used in the CoffeeNet application
 * platform, to enable security features in auto-configuration.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
@FunctionalInterface
public interface ReactiveServerHttpSecurityCustomizer {

    void customize(ServerHttpSecurity http);
}
