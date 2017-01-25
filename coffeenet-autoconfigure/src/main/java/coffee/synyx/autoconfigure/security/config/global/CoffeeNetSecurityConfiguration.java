package coffee.synyx.autoconfigure.security.config.global;

import coffee.synyx.autoconfigure.security.config.development.DevelopmentCoffeeNetSecurityConfiguration;
import coffee.synyx.autoconfigure.security.config.integration.IntegrationCoffeeNetSecurityConfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Security Configuration.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@Import({ IntegrationCoffeeNetSecurityConfiguration.class, DevelopmentCoffeeNetSecurityConfiguration.class })
@ConditionalOnProperty(prefix = "coffeenet.security", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CoffeeNetSecurityConfiguration {
}
