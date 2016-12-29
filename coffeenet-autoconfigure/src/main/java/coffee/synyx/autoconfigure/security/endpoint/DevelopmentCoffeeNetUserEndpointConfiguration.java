package coffee.synyx.autoconfigure.security.endpoint;

import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.DEVELOPMENT;


/**
 * Development configuration to provide the "coffeenet/user" endpoint with a mocked current logged in user.
 *
 * <p>Provides mock beans for {@link CoffeeNetUserService} and {@link CoffeeNetUserEndpoint} for development purposes.
 * </p>
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = DEVELOPMENT, matchIfMissing = true)
public class DevelopmentCoffeeNetUserEndpointConfiguration {

    @Bean
    @Autowired
    public CoffeeNetUserService coffeeNetUserService(CoffeeNetCurrentUserService coffeeNetCurrentUserService) {

        return new DevelopmentCoffeeNetUserService(coffeeNetCurrentUserService);
    }


    @Bean
    @Autowired
    public CoffeeNetUserEndpoint coffeeNetUserEndpoint(CoffeeNetUserService coffeeNetUserService) {

        return new CoffeeNetUserEndpoint(coffeeNetUserService);
    }
}
