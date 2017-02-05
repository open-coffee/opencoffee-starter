package coffee.synyx.autoconfigure.web;

import coffee.synyx.autoconfigure.discovery.config.CoffeeNetDiscoveryAutoConfiguration;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.security.config.CoffeeNetSecurityAutoConfiguration;
import coffee.synyx.autoconfigure.security.service.CoffeeNetCurrentUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * Global configuration class for the web starters to provide all needed services.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
@Configuration
@AutoConfigureAfter({ CoffeeNetDiscoveryAutoConfiguration.class, CoffeeNetSecurityAutoConfiguration.class })
public class CoffeeNetWebAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(lookup().lookupClass());

    @Bean
    public CoffeeNetWebProperties coffeeNetWebProperties() {

        return new CoffeeNetWebProperties();
    }


    @Bean
    @Conditional(ConditionOnSecurityAndDiscovery.class)
    @ConditionalOnMissingBean(CoffeeNetWebService.class)
    public CoffeeNetWebService coffeeNetWebServiceWithSecurityAndDiscovery(
        CoffeeNetCurrentUserService coffeeNetCurrentUserService, CoffeeNetAppService coffeeNetAppService,
        CoffeeNetWebProperties coffeeNetWebProperties) {

        CoffeeNetWebService coffeeNetWebService = new CoffeeNetWebServiceWithDiscoveryAndSecurity(
                coffeeNetCurrentUserService, coffeeNetAppService, coffeeNetWebProperties);

        LOGGER.info(
            "//> Created the CoffeeNetWebService with CoffeeNetWebServiceWithDiscoveryAndSecurity implementation");

        return coffeeNetWebService;
    }


    @Bean
    @Conditional(ConditionOnDiscoveryAndNoSecurity.class)
    @ConditionalOnMissingBean(CoffeeNetWebService.class)
    public CoffeeNetWebService coffeeNetWebServiceWithDiscovery(CoffeeNetAppService coffeeNetAppService,
        CoffeeNetWebProperties coffeeNetWebProperties) {

        CoffeeNetWebService coffeeNetWebService = new CoffeeNetWebServiceWithDiscovery(coffeeNetAppService,
                coffeeNetWebProperties);

        LOGGER.info("//> Created the CoffeeNetWebService with CoffeeNetWebServiceWithDiscovery implementation");

        return coffeeNetWebService;
    }


    @Bean
    @Conditional(ConditionOnSecurityAndNoDiscovery.class)
    @ConditionalOnMissingBean(CoffeeNetWebService.class)
    public CoffeeNetWebService coffeeNetWebServiceWithSecurity(CoffeeNetCurrentUserService coffeeNetCurrentUserService,
        CoffeeNetWebProperties coffeeNetWebProperties) {

        CoffeeNetWebService coffeeNetWebService = new CoffeeNetWebServiceWithSecurity(coffeeNetCurrentUserService,
                coffeeNetWebProperties);

        LOGGER.info("//> Created the CoffeeNetWebService with CoffeeNetWebServiceWithSecurity implementation");

        return coffeeNetWebService;
    }

    /**
     * Configuration class to add the {@link CoffeeNetWebInterceptor} via {@link CoffeeNetWebMvcConfigurerAdapter} when
     * the templates for server side rendering of the navigation bar are provided.
     */
    @Configuration
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
    @ConditionalOnResource(resources = "classpath:/META-INF/resources/webjars/navigation-bar/bundle.js")
    public static class CoffeeNetWebClientSideRenderingConfiguration {

        @Bean
        @Autowired
        @ConditionalOnMissingBean(CoffeeNetWebEndpoint.class)
        public CoffeeNetWebEndpoint coffeeNetWebEndpoint(CoffeeNetWebService coffeeNetWebService) {

            return new CoffeeNetWebEndpoint(coffeeNetWebService);
        }
    }

    /**
     * Security and Discovery bean must be in the bean factory to auto configure the web part.
     */
    static class ConditionOnSecurityAndDiscovery extends AllNestedConditions {

        ConditionOnSecurityAndDiscovery() {

            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnBean(CoffeeNetAppService.class)
        static class OnDiscovery {
        }

        @ConditionalOnBean(CoffeeNetCurrentUserService.class)
        static class OnSecurity {
        }
    }

    static class ConditionOnSecurityAndNoDiscovery extends AllNestedConditions {

        ConditionOnSecurityAndNoDiscovery() {

            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnMissingBean(CoffeeNetAppService.class)
        static class OnDiscovery {
        }

        @ConditionalOnBean(CoffeeNetCurrentUserService.class)
        static class OnSecurity {
        }
    }

    static class ConditionOnDiscoveryAndNoSecurity extends AllNestedConditions {

        ConditionOnDiscoveryAndNoSecurity() {

            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnBean(CoffeeNetAppService.class)
        static class OnDiscovery {
        }

        @ConditionalOnMissingBean(CoffeeNetCurrentUserService.class)
        static class OnSecurity {
        }
    }
}
