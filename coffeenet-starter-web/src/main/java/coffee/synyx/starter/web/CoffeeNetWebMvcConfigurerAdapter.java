package coffee.synyx.starter.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(lookup().lookupClass());

    private CoffeeNetWebInterceptor coffeeNetWebInterceptor;

    CoffeeNetWebMvcConfigurerAdapter(CoffeeNetWebInterceptor coffeeNetWebInterceptor) {

        this.coffeeNetWebInterceptor = coffeeNetWebInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(coffeeNetWebInterceptor).addPathPatterns("/**");

        LOGGER.info("//> Added the CoffeeNetWebInterceptor");
    }
}
