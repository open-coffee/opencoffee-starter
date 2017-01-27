package coffee.synyx.autoconfigure.web;

import coffee.synyx.autoconfigure.discovery.config.CoffeeNetDiscoveryAutoConfiguration;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.security.config.CoffeeNetSecurityAutoConfiguration;
import coffee.synyx.autoconfigure.security.service.CoffeeNetCurrentUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;

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
@ConditionalOnBean(CoffeeNetAppService.class)
@AutoConfigureAfter({ CoffeeNetDiscoveryAutoConfiguration.class, CoffeeNetSecurityAutoConfiguration.class })
public class CoffeeNetWebAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(lookup().lookupClass());

    @Bean
    @ConditionalOnBean(CoffeeNetCurrentUserService.class)
    public CoffeeNetWebProperties coffeeNetWebProperties() {

        return new CoffeeNetWebProperties();
    }


    @Bean
    @ConditionalOnBean(CoffeeNetCurrentUserService.class)
    @ConditionalOnMissingBean(CoffeeNetWebService.class)
    public CoffeeNetWebService coffeeNetWebService(CoffeeNetCurrentUserService coffeeNetCurrentUserService,
        CoffeeNetAppService coffeeNetAppService, CoffeeNetWebProperties coffeeNetWebProperties) {

        CoffeeNetWebServiceImpl coffeeNetWebService = new CoffeeNetWebServiceImpl(coffeeNetCurrentUserService,
                coffeeNetAppService, coffeeNetWebProperties);

        LOGGER.info("//> Created the CoffeeNetWebService");

        return coffeeNetWebService;
    }

    /**
     * Configuration class to add the {@link CoffeeNetWebInterceptor} via {@link CoffeeNetWebMvcConfigurerAdapter} when
     * the templates for server side rendering of the navigation bar are provided.
     */
    @Configuration
    @ConditionalOnBean(CoffeeNetCurrentUserService.class)
    @ConditionalOnResource(resources = "classpath:/templates/coffeenet/_layout.html")
    public static class CoffeeNetWebServerSideRenderingConfiguration {

        @Bean
        @Autowired
        @ConditionalOnMissingBean(CoffeeNetWebInterceptor.class)
        public CoffeeNetWebInterceptor coffeeNetInterceptor(CoffeeNetWebService coffeeNetWebService) {

            CoffeeNetWebInterceptor coffeeNetWebInterceptor = new CoffeeNetWebInterceptor(coffeeNetWebService);

            LOGGER.info("//> Created the CoffeeNet handler interceptor");

            return coffeeNetWebInterceptor;
        }


        @Bean
        @Autowired
        @ConditionalOnMissingBean(CoffeeNetWebMvcConfigurerAdapter.class)
        public CoffeeNetWebMvcConfigurerAdapter coffeeNetWebMvcConfigurerAdapter(
            CoffeeNetWebInterceptor coffeeNetWebInterceptor) {

            CoffeeNetWebMvcConfigurerAdapter coffeeNetWebMvcConfigurerAdapter = new CoffeeNetWebMvcConfigurerAdapter(
                    coffeeNetWebInterceptor);

            LOGGER.info("//> Created the CoffeeNetWebMvcConfigurerAdapter");

            return coffeeNetWebMvcConfigurerAdapter;
        }
    }

    /**
     * Provides all Endpoints that are needed to provide all information for the javascript CoffeeNet navigation.
     */
    @Configuration
    @ConditionalOnBean(CoffeeNetCurrentUserService.class)
    @ConditionalOnResource(resources = "classpath:/META-INF/resources/webjars/navigation-bar/bundle.js")
    public static class CoffeeNetWebClientSideRenderingConfiguration {

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
}
