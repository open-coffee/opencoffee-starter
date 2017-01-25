package coffee.synyx.autoconfigure.web.client;

import coffee.synyx.autoconfigure.web.CoffeeNetWebService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Provides all Endpoints that are needed to provide all information for the javascript CoffeeNet navigation.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@ConditionalOnResource(resources = "classpath:/META-INF/resources/webjars/navigation-bar/bundle.js")
public class CoffeeNetWebClientSideRenderingConfiguration {

    @Bean
    @Autowired
    @ConditionalOnMissingBean(CoffeeNetUserEndpoint.class)
    public CoffeeNetUserEndpoint coffeeNetUserEndpoint(CoffeeNetWebService coffeeNetWebService) {

        return new CoffeeNetUserEndpoint(coffeeNetWebService);
    }


    @Bean
    @Autowired
    @ConditionalOnMissingBean(CoffeeNetAppsEndpoint.class)
    public CoffeeNetAppsEndpoint coffeeNetAppsEndpoint(CoffeeNetWebService coffeeNetWebService) {

        return new CoffeeNetAppsEndpoint(coffeeNetWebService);
    }


    @Bean
    @Autowired
    @ConditionalOnMissingBean(CoffeeNetWebEndpoint.class)
    public CoffeeNetWebEndpoint coffeeNetWebEndpoint(CoffeeNetWebService coffeeNetWebService) {

        return new CoffeeNetWebEndpoint(coffeeNetWebService);
    }
}
