package coffee.synyx.autoconfigure.discovery.config.global;

import coffee.synyx.autoconfigure.discovery.config.development.DevelopmentCoffeeNetServiceDiscoveryConfiguration;
import coffee.synyx.autoconfigure.discovery.config.integration.CoffeeNetDiscoveryPropertiesConfiguration;
import coffee.synyx.autoconfigure.discovery.config.integration.IntegrationCoffeeNetServiceDiscoveryConfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Discovery Configuration.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@Import(
    {
        IntegrationCoffeeNetServiceDiscoveryConfiguration.class,
        DevelopmentCoffeeNetServiceDiscoveryConfiguration.class, CoffeeNetDiscoveryPropertiesConfiguration.class
    }
)
@ConditionalOnProperty(prefix = "coffeenet.discovery", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CoffeeNetDiscoveryConfiguration {
}
