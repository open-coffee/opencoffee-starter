package coffee.synyx.autoconfigure.security.config.development;

import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.user.DevelopmentCoffeeNetCurrentUserService;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.DEVELOPMENT;


/**
 * Development security mock instantiation.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@ConditionalOnClass({ OAuth2ClientContext.class, WebSecurityConfigurerAdapter.class })
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = DEVELOPMENT, matchIfMissing = true)
public class DevelopmentCoffeeNetSecurityConfiguration {

    @Bean
    public CoffeeNetCurrentUserService coffeeNetCurrentUserService() {

        return new DevelopmentCoffeeNetCurrentUserService();
    }


    @Bean
    @ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
    public DevelopmentCoffeeNetWebSecurityConfigurerAdapter coffeeWebSecurityConfigurerAdapter() {

        return new DevelopmentCoffeeNetWebSecurityConfigurerAdapter();
    }
}
