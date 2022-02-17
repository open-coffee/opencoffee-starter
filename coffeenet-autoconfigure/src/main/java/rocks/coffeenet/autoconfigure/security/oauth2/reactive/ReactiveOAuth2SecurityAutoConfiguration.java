package rocks.coffeenet.autoconfigure.security.oauth2.reactive;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.ClientsConfiguredCondition;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

import reactor.core.publisher.Flux;


/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Security OAuth2 in CoffeeNet applications.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(ReactiveSecurityAutoConfiguration.class)
@ConditionalOnClass({ Flux.class, EnableWebFluxSecurity.class, ClientRegistration.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@Conditional(ClientsConfiguredCondition.class)
public class ReactiveOAuth2SecurityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ReactiveOAuth2SecurityCustomizer.class)
    public ReactiveOAuth2SecurityCustomizer coffeeNetReactiveOAuth2SecurityCustomizer() {

        return new ReactiveOAuth2SecurityCustomizer();
    }
}
