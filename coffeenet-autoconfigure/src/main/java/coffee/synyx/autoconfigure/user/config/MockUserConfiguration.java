package coffee.synyx.autoconfigure.user.config;

import coffee.synyx.autoconfigure.user.endpoint.UserEndpoint;
import coffee.synyx.autoconfigure.user.service.MockUserServiceImpl;
import coffee.synyx.autoconfigure.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.DEVELOPMENT;


/**
 * Provides Mock Beans for {@link UserService} and {@link UserEndpoint} for development purposes.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = DEVELOPMENT, matchIfMissing = true)
public class MockUserConfiguration {

    @Bean
    public UserService coffeeNetUserService() {

        return new MockUserServiceImpl();
    }


    @Bean
    @Autowired
    public UserEndpoint coffeeNetUserEndpoint(UserService userService) {

        return new UserEndpoint(userService);
    }
}
