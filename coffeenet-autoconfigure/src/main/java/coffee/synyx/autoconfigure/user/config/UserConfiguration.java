package coffee.synyx.autoconfigure.user.config;

import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.user.endpoint.UserEndpoint;
import coffee.synyx.autoconfigure.user.service.UserService;
import coffee.synyx.autoconfigure.user.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.oauth2.client.OAuth2ClientContext;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.INTEGRATION;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  David Schilling - schilling@synyx.de
 */
@Configuration
@ConditionalOnClass(OAuth2ClientContext.class)
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = INTEGRATION)
public class UserConfiguration {

    @Bean
    @Autowired
    public UserService coffeeNetUserService(CoffeeNetCurrentUserService coffeeNetCurrentUserService) {

        return new UserServiceImpl(coffeeNetCurrentUserService);
    }


    @Bean
    @Autowired
    public UserEndpoint coffeeNetUserEndpoint(UserService userService) {

        return new UserEndpoint(userService);
    }
}
