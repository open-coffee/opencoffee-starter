package coffee.synyx.autoconfigure.web;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.security.service.CoffeeNetCurrentUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * Global configuration class for the web starters to provide all needed services.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
@Configuration
public class CoffeeNetWebConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(lookup().lookupClass());

    @Bean
    CoffeeNetWebProperties coffeeNetWebProperties() {

        return new CoffeeNetWebProperties();
    }


    @Bean
    @Autowired
    @ConditionalOnBean({ CoffeeNetCurrentUserService.class, CoffeeNetAppService.class })
    @ConditionalOnMissingBean(CoffeeNetWebService.class)
    CoffeeNetWebService coffeeNetWebService(CoffeeNetCurrentUserService coffeeNetCurrentUserService,
        CoffeeNetAppService coffeeNetAppService, CoffeeNetWebProperties coffeeNetWebProperties) {

        CoffeeNetWebServiceImpl coffeeNetWebService = new CoffeeNetWebServiceImpl(coffeeNetCurrentUserService,
                coffeeNetAppService, coffeeNetWebProperties);

        LOGGER.info("//> Created the CoffeeNetWebService");

        return coffeeNetWebService;
    }
}
