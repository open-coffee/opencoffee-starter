package coffee.synyx.autoconfigure.web.server;

import coffee.synyx.autoconfigure.web.CoffeeNetWebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * Configuration class to add the {@link CoffeeNetWebInterceptor} via {@link CoffeeNetWebMvcConfigurerAdapter} when the
 * templates for server side rendering of the navigation bar are provided.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
@Configuration
@ConditionalOnResource(resources = "classpath:/templates/coffeenet/_layout.html")
public class CoffeeNetWebServerSideRenderingConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(lookup().lookupClass());

    @Bean
    @Autowired
    @ConditionalOnMissingBean(CoffeeNetWebInterceptor.class)
    CoffeeNetWebInterceptor coffeeNetInterceptor(CoffeeNetWebService coffeeNetWebService) {

        CoffeeNetWebInterceptor coffeeNetWebInterceptor = new CoffeeNetWebInterceptor(coffeeNetWebService);

        LOGGER.info("//> Created the CoffeeNet handler interceptor");

        return coffeeNetWebInterceptor;
    }


    @Bean
    @Autowired
    @ConditionalOnMissingBean(CoffeeNetWebMvcConfigurerAdapter.class)
    CoffeeNetWebMvcConfigurerAdapter coffeeNetWebMvcConfigurerAdapter(CoffeeNetWebInterceptor coffeeNetWebInterceptor) {

        CoffeeNetWebMvcConfigurerAdapter coffeeNetWebMvcConfigurerAdapter = new CoffeeNetWebMvcConfigurerAdapter(
                coffeeNetWebInterceptor);

        LOGGER.info("//> Created the CoffeeNetWebMvcConfigurerAdapter");

        return coffeeNetWebMvcConfigurerAdapter;
    }
}
