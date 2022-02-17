package rocks.coffeenet.autoconfigure.security.reactive;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.server.WebFilterChainProxy;

import org.springframework.web.reactive.config.WebFluxConfigurer;

import reactor.core.publisher.Flux;

import java.util.List;


/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Security in reactive CoffeeNet applications.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Flux.class, EnableWebFluxSecurity.class, WebFilterChainProxy.class, WebFluxConfigurer.class })
@AutoConfigureBefore(ReactiveSecurityAutoConfiguration.class)
public class ReactiveCoffeeNetSecurityAutoConfiguration {

    @Bean
    @ConditionalOnBean(ReactiveServerHttpSecurityCustomizer.class)
    public ReactiveServerHttpSecurityPostProcessor coffeeNetReactiveSecurityPostProcessor(
        List<ReactiveServerHttpSecurityCustomizer> customizers) {

        return new ReactiveServerHttpSecurityPostProcessor(customizers);
    }
}
