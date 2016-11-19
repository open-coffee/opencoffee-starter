package coffee.synyx.autoconfigure.security.config;

import coffee.synyx.autoconfigure.security.config.development.DevelopmentCoffeeNetSecurityConfiguration;
import coffee.synyx.autoconfigure.security.config.integration.IntegrationCoffeeNetSecurityConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Security Configuration.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@Import({ IntegrationCoffeeNetSecurityConfiguration.class, DevelopmentCoffeeNetSecurityConfiguration.class })
public class CoffeeNetSecurityConfiguration {
}
