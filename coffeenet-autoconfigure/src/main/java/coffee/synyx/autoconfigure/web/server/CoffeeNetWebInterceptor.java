package coffee.synyx.autoconfigure.web.server;

import coffee.synyx.autoconfigure.web.CoffeeNetWebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.invoke.MethodHandles.lookup;

import static java.util.Optional.ofNullable;


/**
 * This provided CoffeeNet {@link HandlerInterceptorAdapter} intercepts all communication between the application
 * controllers and the frontend to provide all necessary information for the frontend with server side rendering engines
 * like thymeleaf. Does only intercept if the request is not a {@code redirect} and contains a {@link ModelAndView}
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
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
