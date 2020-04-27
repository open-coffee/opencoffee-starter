package rocks.coffeenet.autoconfigure.security;

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
public class ProfileMapperTestConfiguration {

    @Bean
    public PrincipalCoffeeNetProfileMapper testMapper() {

        return p -> CoffeeNetProfile.withUniqueIdentifierAndName("id", p.getName()).build();
    }
}
