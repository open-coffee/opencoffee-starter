package rocks.coffeenet.autoconfigure.security.reactive;

import org.springframework.core.MethodParameter;

import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import rocks.coffeenet.platform.domain.profile.CoffeeNetProfile;
import rocks.coffeenet.platform.domain.profile.PrincipalCoffeeNetProfileMapper;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class ReactiveCoffeeNetProfileArgumentResolver implements HandlerMethodArgumentResolver {

    private final PrincipalCoffeeNetProfileMapper profileMapper;

    public ReactiveCoffeeNetProfileArgumentResolver(PrincipalCoffeeNetProfileMapper profileMapper) {

        this.profileMapper = profileMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return CoffeeNetProfile.class.equals(parameter.getParameterType());
    }


    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext,
        ServerWebExchange exchange) {

        return exchange.getPrincipal()
            .filter((p) -> profileMapper.supports(p.getClass()))
            .map(profileMapper::map);
    }
}
