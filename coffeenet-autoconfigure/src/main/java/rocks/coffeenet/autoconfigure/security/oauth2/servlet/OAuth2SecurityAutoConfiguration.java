package rocks.coffeenet.autoconfigure.security.oauth2.servlet;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.ClientsConfiguredCondition;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

import rocks.coffeenet.autoconfigure.security.oauth2.OAuth2AuthenticationTokenProfileMapper;

import rocks.coffeenet.platform.domain.profile.PrincipalCoffeeNetProfileMapper;


/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Security OAuth2 in CoffeeNet applications.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(SecurityAutoConfiguration.class)
@ConditionalOnClass({ EnableWebSecurity.class, ClientRegistration.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Conditional(ClientsConfiguredCondition.class)
public class OAuth2SecurityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(OAuth2SecurityConfigurer.class)
    public OAuth2SecurityConfigurer coffeeNetOAuth2SecurityConfigurer() {

        return new OAuth2SecurityConfigurer();
    }


    @Bean
    @ConditionalOnMissingBean(OAuth2AuthenticationTokenProfileMapper.class)
    public PrincipalCoffeeNetProfileMapper oauth2AuthenticationTokenProfileMapper() {

        return new OAuth2AuthenticationTokenProfileMapper();
    }
}
