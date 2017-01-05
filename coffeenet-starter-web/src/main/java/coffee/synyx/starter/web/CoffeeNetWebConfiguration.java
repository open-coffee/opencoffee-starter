package coffee.synyx.starter.web;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.security.service.CoffeeNetCurrentUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.invoke.MethodHandles.lookup;


@Configuration
public class CoffeeNetWebConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(lookup().lookupClass());

    @Bean
    @Autowired
    CoffeeNetWebService coffeeNetWebService(CoffeeNetCurrentUserService coffeeNetCurrentUserService,
        CoffeeNetAppService coffeeNetAppService, CoffeeNetWebProperties coffeeNetWebProperties) {

        CoffeeNetWebServiceImpl coffeeNetWebService = new CoffeeNetWebServiceImpl(coffeeNetCurrentUserService,
                coffeeNetAppService, coffeeNetWebProperties);

        LOGGER.info("//> Created the CoffeeNetWebService");

        return coffeeNetWebService;
    }


    @Bean
    @Autowired
    CoffeeNetWebInterceptor coffeeNetInterceptor(CoffeeNetWebService coffeeNetWebService) {

        CoffeeNetWebInterceptor coffeeNetWebInterceptor = new CoffeeNetWebInterceptor(coffeeNetWebService);

        LOGGER.info("//> Created the CoffeeNet handler interceptor");

        return coffeeNetWebInterceptor;
    }


    @Bean
    @Autowired
    CoffeeNetWebMvcConfigurerAdapter coffeeNetWebMvcConfigurerAdapter(CoffeeNetWebInterceptor coffeeNetWebInterceptor) {

        CoffeeNetWebMvcConfigurerAdapter coffeeNetWebMvcConfigurerAdapter = new CoffeeNetWebMvcConfigurerAdapter(
                coffeeNetWebInterceptor);

        LOGGER.info("//> Created the CoffeeNetWebMvcConfigurerAdapter");

        return coffeeNetWebMvcConfigurerAdapter;
    }


    @Bean
    CoffeeNetWebProperties coffeeNetWebProperties() {

        return new CoffeeNetWebProperties();
    }
}
