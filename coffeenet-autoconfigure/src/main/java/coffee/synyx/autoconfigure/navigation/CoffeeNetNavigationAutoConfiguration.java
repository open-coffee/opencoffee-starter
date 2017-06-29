package coffee.synyx.autoconfigure.navigation;

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

import static coffee.synyx.autoconfigure.navigation.CoffeeNetNavigationDataExtractor.CoffeeNetServices.APP_SERVICE;
import static coffee.synyx.autoconfigure.navigation.CoffeeNetNavigationDataExtractor.CoffeeNetServices.USER_SERVICE;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * Global configuration class for the navigation starters to provide all needed services.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
@Configuration
@ConditionalOnResource(resources = "classpath:/static/css/coffeenet-navbar.css")
@AutoConfigureAfter({ CoffeeNetDiscoveryAutoConfiguration.class, CoffeeNetSecurityAutoConfiguration.class })
public class CoffeeNetNavigationAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(lookup().lookupClass());

    @Bean
    public CoffeeNetNavigationProperties coffeeNetNavigationProperties() {

        return new CoffeeNetNavigationProperties();
    }


    @Bean
    @ConditionalOnMissingBean(CoffeeNetNavigationDataExtractor.class)
    public CoffeeNetNavigationDataExtractor coffeeNetNavigationDataExtractor(
        CoffeeNetNavigationProperties coffeeNetNavigationProperties) {

        CoffeeNetNavigationDataExtractor coffeeNetNavigationDataExtractor = new CoffeeNetNavigationDataExtractor(
                coffeeNetNavigationProperties);

        LOGGER.info("//> Created the CoffeeNetNavigationDataExtractor");

        return coffeeNetNavigationDataExtractor;
    }


    @Bean
    @ConditionalOnMissingBean(CoffeeNetNavigationService.class)
    public CoffeeNetNavigationService coffeeNetWebService(
        CoffeeNetNavigationDataExtractor coffeeNetNavigationDataExtractor) {

        CoffeeNetNavigationService coffeeNetNavigationService = new CoffeeNetNavigationServiceImpl(
                coffeeNetNavigationDataExtractor);

        LOGGER.info("//> Created the CoffeeNetNavigationService");

        return coffeeNetNavigationService;
    }

    @Configuration
    @ConditionalOnBean(CoffeeNetCurrentUserService.class)
    static class CoffeeNetCurrentUserServiceConfiguration {

        @Autowired
        CoffeeNetCurrentUserServiceConfiguration(CoffeeNetNavigationDataExtractor coffeeNetNavigationDataExtractor,
            CoffeeNetCurrentUserService coffeeNetCurrentUserService) {

            coffeeNetNavigationDataExtractor.registerService(USER_SERVICE, coffeeNetCurrentUserService);

            LOGGER.info("//> Added the CoffeeNetCurrentUserService to the CoffeeNetNavigationDataExtractor");
        }
    }

    @Configuration
    @ConditionalOnBean(CoffeeNetAppService.class)
    static class CoffeeNetAppServiceConfiguration {

        @Autowired
        CoffeeNetAppServiceConfiguration(CoffeeNetNavigationDataExtractor coffeeNetNavigationDataExtractor,
            CoffeeNetAppService coffeeNetAppService) {

            coffeeNetNavigationDataExtractor.registerService(APP_SERVICE, coffeeNetAppService);

            LOGGER.info("//> Added the CoffeeNetAppService to the CoffeeNetNavigationDataExtractor");
        }
    }

    /**
     * Configuration class to add the {@link CoffeeNetNavigationInterceptor} via
     * {@link CoffeeNetWebMvcConfigurerAdapter} when the templates for server side rendering of the navigation bar are
     * provided.
     */
    @Configuration
    @ConditionalOnResource(resources = "classpath:/templates/coffeenet/_layout.html")
    public static class CoffeeNetWebServerSideRenderingConfiguration {

        @Bean
        @Autowired
        @ConditionalOnMissingBean(CoffeeNetNavigationInterceptor.class)
        public CoffeeNetNavigationInterceptor coffeeNetInterceptor(
            CoffeeNetNavigationService coffeeNetNavigationService) {

            CoffeeNetNavigationInterceptor coffeeNetNavigationInterceptor = new CoffeeNetNavigationInterceptor(
                    coffeeNetNavigationService);

            LOGGER.info("//> Created the CoffeeNet handler interceptor");

            return coffeeNetNavigationInterceptor;
        }


        @Bean
        @Autowired
        @ConditionalOnMissingBean(CoffeeNetWebMvcConfigurerAdapter.class)
        public CoffeeNetWebMvcConfigurerAdapter coffeeNetWebMvcConfigurerAdapter(
            CoffeeNetNavigationInterceptor coffeeNetNavigationInterceptor) {

            CoffeeNetWebMvcConfigurerAdapter coffeeNetWebMvcConfigurerAdapter = new CoffeeNetWebMvcConfigurerAdapter(
                    coffeeNetNavigationInterceptor);

            LOGGER.info("//> Created the CoffeeNetWebMvcConfigurerAdapter");

            return coffeeNetWebMvcConfigurerAdapter;
        }
    }

    /**
     * Provides all Endpoints that are needed to provide all information for the javascript CoffeeNet navigation.
     */
    @Configuration
    @ConditionalOnResource(resources = "classpath:/META-INF/resources/webjars/navigation-bar/bundle.js")
    public static class CoffeeNetWebClientSideRenderingConfiguration {

        @Bean
        @Autowired
        @ConditionalOnMissingBean(CoffeeNetNavigationEndpoint.class)
        public CoffeeNetNavigationEndpoint coffeeNetWebEndpoint(
            CoffeeNetNavigationService coffeeNetNavigationService) {

            return new CoffeeNetNavigationEndpoint(coffeeNetNavigationService);
        }
    }
}
