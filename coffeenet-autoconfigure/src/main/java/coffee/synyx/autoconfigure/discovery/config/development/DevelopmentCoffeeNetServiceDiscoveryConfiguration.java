package coffee.synyx.autoconfigure.discovery.config.development;

import coffee.synyx.autoconfigure.discovery.endpoint.CoffeeNetAppsEndpoint;
import coffee.synyx.autoconfigure.discovery.endpoint.CoffeeNetAppsEndpointNoFilter;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.discovery.service.DevelopmentCoffeeNetAppService;
import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;

import com.netflix.discovery.DiscoveryClient;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.DEVELOPMENT;


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
    @ConditionalOnMissingBean(CoffeeNetCurrentUserService.class)
    public CoffeeNetAppsEndpointNoFilter coffeeNetAppsEndpoint() {

        return new CoffeeNetAppsEndpointNoFilter(coffeeNetAppService());
    }


    @Bean
    @Autowired
    @ConditionalOnBean(CoffeeNetCurrentUserService.class)
    public CoffeeNetAppsEndpoint coffeeNetAppsEndpointWithFilter(CoffeeNetCurrentUserService currentUserService) {

        return new CoffeeNetAppsEndpoint(coffeeNetAppService(), currentUserService);
    }
}
