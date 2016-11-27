package coffee.synyx.autoconfigure.discovery.config.development;

import coffee.synyx.autoconfigure.discovery.endpoint.CoffeeNetAppsEndpoint;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.discovery.service.DevelopmentCoffeeNetAppService;

import com.netflix.discovery.DiscoveryClient;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.DEVELOPMENT;

import static java.util.Collections.emptySet;


/**
 * Development service discovery mock bean instantiation.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@ConditionalOnClass(DiscoveryClient.class)
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = DEVELOPMENT, matchIfMissing = true)
public class DevelopmentCoffeeNetServiceDiscoveryConfiguration {

    @Bean
    @ConditionalOnMissingBean(CoffeeNetAppService.class)
    public CoffeeNetAppService coffeeNetAppService() {

        return new DevelopmentCoffeeNetAppService();
    }


    @Bean
    @ConditionalOnMissingBean(CoffeeNetAppsEndpoint.class)
    public CoffeeNetAppsEndpoint coffeeNetAppsEndpoint() {

        return new CoffeeNetAppsEndpoint(coffeeNetAppService(), emptySet());
    }
}
