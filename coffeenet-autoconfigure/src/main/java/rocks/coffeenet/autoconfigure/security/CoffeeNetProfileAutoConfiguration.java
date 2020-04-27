package rocks.coffeenet.autoconfigure.security;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import rocks.coffeenet.autoconfigure.security.reactive.ReactiveCoffeeNetProfileArgumentResolver;
import rocks.coffeenet.autoconfigure.security.servlet.CoffeeNetProfileArgumentResolver;

import rocks.coffeenet.platform.domain.profile.CoffeeNetProfile;
import rocks.coffeenet.platform.domain.profile.DelegatingPrincipalCoffeeNetProfileMapper;
import rocks.coffeenet.platform.domain.profile.PrincipalCoffeeNetProfileMapper;

import java.util.List;


/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link CoffeeNetProfile} support in CoffeeNet applications.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({ SecurityAutoConfiguration.class, ReactiveSecurityAutoConfiguration.class })
@ConditionalOnClass(DefaultAuthenticationEventPublisher.class)
@Import(FallbackProfileMapperConfiguration.class)
public class CoffeeNetProfileAutoConfiguration {

    @Bean
    @Primary
    @ConditionalOnBean(PrincipalCoffeeNetProfileMapper.class)
    @ConditionalOnMissingBean(DelegatingPrincipalCoffeeNetProfileMapper.class)
    PrincipalCoffeeNetProfileMapper delegatingPrincipalCoffeeNetProfileMapper(
        List<PrincipalCoffeeNetProfileMapper> mappers) {

        return new DelegatingPrincipalCoffeeNetProfileMapper(mappers);
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnBean(
        value = { PrincipalCoffeeNetProfileMapper.class },
        name = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME
    )
    static class CoffeeNetProfileArgumentResolverConfigurer implements WebMvcConfigurer {

        private final PrincipalCoffeeNetProfileMapper mapper;

        CoffeeNetProfileArgumentResolverConfigurer(PrincipalCoffeeNetProfileMapper mapper) {

            this.mapper = mapper;
        }

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

            CoffeeNetProfileArgumentResolver resolver = new CoffeeNetProfileArgumentResolver(mapper);
            resolvers.add(0, resolver);
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @ConditionalOnBean(PrincipalCoffeeNetProfileMapper.class)
    static class ReactiveCoffeeNetProfileArgumentResolverConfiguration {

        @Bean
        @ConditionalOnMissingBean(ReactiveCoffeeNetProfileArgumentResolver.class)
        public ReactiveCoffeeNetProfileArgumentResolver reactiveCoffeeNetProfileArgumentResolver(
            PrincipalCoffeeNetProfileMapper mapper) {

            return new ReactiveCoffeeNetProfileArgumentResolver(mapper);
        }
    }
}
