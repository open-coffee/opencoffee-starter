package coffee.synyx.autoconfigure.security.endpoint;

import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.INTEGRATION;


/**
 * Integrative configuration to provide the "coffeenet/user" endpoint with the current logged in user.*
 *
 * <p>Provides beans for {@link CoffeeNetUserService} and {@link CoffeeNetUserEndpoint} for integration purposes. Will
 * only configure the endpoint under {@code /coffeenet/user} when a {@code CoffeeNetCurrentUserService} is on the
 * classpath.</p>
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  David Schilling - schilling@synyx.de
 */
@Configuration
@ConditionalOnBean(CoffeeNetCurrentUserService.class)
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = INTEGRATION)
public class IntegrationCoffeeNetUserEndpointConfiguration {

    @Bean
    @Autowired
    public CoffeeNetUserService coffeeNetUserService(CoffeeNetCurrentUserService coffeeNetCurrentUserService) {

        return new IntegrationCoffeeNetUserService(coffeeNetCurrentUserService);
    }


    @Bean
    @Autowired
    public CoffeeNetUserEndpoint coffeeNetUserEndpoint(CoffeeNetUserService coffeeNetUserService) {

        return new CoffeeNetUserEndpoint(coffeeNetUserService);
    }
}
