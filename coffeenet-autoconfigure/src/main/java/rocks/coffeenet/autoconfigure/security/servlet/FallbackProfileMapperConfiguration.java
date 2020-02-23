package rocks.coffeenet.autoconfigure.security.servlet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rocks.coffeenet.autoconfigure.security.DefaultPrincipalCoffeenetProfileMapper;

import rocks.coffeenet.platform.domain.profile.PrincipalCoffeeNetProfileMapper;


/**
 * Register a fallback profile mapper in case no others have been registered.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@Configuration
@ConditionalOnMissingBean(PrincipalCoffeeNetProfileMapper.class)
public class FallbackProfileMapperConfiguration {

    @Bean
    PrincipalCoffeeNetProfileMapper fallbackPrincipalCoffeeNetProfileMapper() {

        return new DefaultPrincipalCoffeenetProfileMapper();
    }
}
