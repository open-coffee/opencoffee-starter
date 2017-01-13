package coffee.synyx.autoconfigure.web.server;

import coffee.synyx.autoconfigure.web.web.CoffeeNetWebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.invoke.MethodHandles.lookup;

import static java.util.Optional.ofNullable;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetWebInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(lookup().lookupClass());

    private final CoffeeNetWebService coffeeNetWebService;

    CoffeeNetWebInterceptor(CoffeeNetWebService coffeeNetWebService) {

        this.coffeeNetWebService = coffeeNetWebService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {

        if (modelAndView == null) {
            LOGGER.debug("//> Ignoring intercepted empty model and view for request: {}", request);

            return;
        }

        String view = ofNullable(modelAndView.getViewName()).orElse("");

        boolean isNotRedirect = !view.contains("redirect:");

        if (isNotRedirect) {
            modelAndView.addObject("coffeenet", coffeeNetWebService.get());

            LOGGER.debug("//> Added CoffeeNetWeb to the intercepted model and view '{}'", view);
        } else {
            LOGGER.debug("//> Ignoring intercepted view target: '{}'", view);
        }
    }
}
