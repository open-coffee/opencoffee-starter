package rocks.coffeenet.autoconfigure.security.servlet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rocks.coffeenet.platform.domain.profile.CoffeeNetProfile;
import rocks.coffeenet.platform.domain.profile.PrincipalCoffeeNetProfileMapper;


/**
 * An auto-configuration helper to configure a simple {@link PrincipalCoffeeNetProfileMapper} for use in tests.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@Configuration
class ProfileMapperTestConfiguration {

    @Bean
    PrincipalCoffeeNetProfileMapper testMapper() {

        return p -> CoffeeNetProfile.withUniqueIdentifierAndName("id", p.getName()).build();
    }
}
