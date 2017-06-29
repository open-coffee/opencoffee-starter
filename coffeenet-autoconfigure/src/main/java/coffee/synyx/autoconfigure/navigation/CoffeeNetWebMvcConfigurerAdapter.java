package coffee.synyx.autoconfigure.navigation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * Register the {@link CoffeeNetWebInterceptor} as {@link HandlerInterceptor} for all requests.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
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
