package coffee.synyx.autoconfigure.discovery.config.integration;

import coffee.synyx.autoconfigure.discovery.config.CoffeeNetServiceDiscoveryConfiguration;
import coffee.synyx.autoconfigure.discovery.endpoint.CoffeeNetAppsEndpoint;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.discovery.service.IntegrationEurekaCoffeeNetAppService;
import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.INTEGRATION;


/**
 * Production service discovery bean instantiation.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@EnableEurekaClient
@ConditionalOnClass(DiscoveryClient.class)
@ConditionalOnMissingBean(CoffeeNetServiceDiscoveryConfiguration.class)
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = INTEGRATION)
public class IntegrationCoffeeNetServiceDiscoveryConfiguration implements CoffeeNetServiceDiscoveryConfiguration {

    private final DiscoveryClient discoveryClient;

    @Autowired
    public IntegrationCoffeeNetServiceDiscoveryConfiguration(DiscoveryClient discoveryClient) {

        this.discoveryClient = discoveryClient;
    }

    @Bean
    @Override
    public CoffeeNetAppService coffeeNetAppService() {

        return new IntegrationEurekaCoffeeNetAppService(discoveryClient);
    }


    @Bean
    @Override
    @Autowired
    public CoffeeNetAppsEndpoint coffeeNetAppsEndpoint(CoffeeNetCurrentUserService coffeeNetCurrentUserService) {

        return new CoffeeNetAppsEndpoint(coffeeNetAppService(), coffeeNetCurrentUserService);
    }
}
