package rocks.coffeenet.autoconfigure.security.servlet;

import org.springframework.core.MethodParameter;

import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import rocks.coffeenet.platform.domain.profile.CoffeeNetProfile;
import rocks.coffeenet.platform.domain.profile.PrincipalCoffeeNetProfileMapper;

import java.security.Principal;


/**
 * Allow injection of the current {@link CoffeeNetProfile} into {@link org.springframework.stereotype.Controller}
 * methods.
 *
 * <p>If there a profile cannot be determined (not logged in, no anonymous mapping), it will simply provide
 * {@code null}.</p>
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
public class CoffeeNetProfileArgumentResolver implements HandlerMethodArgumentResolver {

    private final PrincipalCoffeeNetProfileMapper profileMapper;

    public CoffeeNetProfileArgumentResolver(PrincipalCoffeeNetProfileMapper profileMapper) {

        this.profileMapper = profileMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return CoffeeNetProfile.class.equals(parameter.getParameterType());
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Principal principal = webRequest.getUserPrincipal();

        if (principal == null) {
            return null;
        }

        return profileMapper.supports(principal.getClass()) ? profileMapper.map(principal) : null;
    }
}
