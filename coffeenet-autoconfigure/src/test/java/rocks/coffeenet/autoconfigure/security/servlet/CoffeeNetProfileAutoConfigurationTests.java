package rocks.coffeenet.autoconfigure.security.servlet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import rocks.coffeenet.autoconfigure.security.servlet.CoffeeNetProfileAutoConfiguration.CoffeeNetProfileArgumentResolverConfigurer;

import rocks.coffeenet.platform.domain.profile.DelegatingPrincipalCoffeeNetProfileMapper;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName("CoffeeNetProfileAutoConfiguration")
class CoffeeNetProfileAutoConfigurationTests {

    @Test
    @DisplayName("should not install profile mapper delegator if no mappers present")
    void noProfileMappersDefined() {

        WebApplicationContextRunner runner =
            new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(SecurityAutoConfiguration.class,
                        CoffeeNetProfileAutoConfiguration.class));

        runner.run(context -> assertThat(context).doesNotHaveBean(DelegatingPrincipalCoffeeNetProfileMapper.class));
    }


    @Test
    @DisplayName("should install profile mapper delegator if mappers present")
    void profileMappersDefined() {

        WebApplicationContextRunner runner = new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(SecurityAutoConfiguration.class,
                            CoffeeNetProfileAutoConfiguration.class))
                .withUserConfiguration(ProfileMapperTestConfiguration.class);

        runner.run(context -> assertThat(context).hasSingleBean(DelegatingPrincipalCoffeeNetProfileMapper.class));
    }


    @Test
    @DisplayName("should configure a handler method argument resolver for profiles")
    void webappContext() {

        WebApplicationContextRunner runner = new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(SecurityAutoConfiguration.class,
                            CoffeeNetProfileAutoConfiguration.class))
                .withUserConfiguration(ProfileMapperTestConfiguration.class);

        runner.run(context -> {
            assertThat(context).hasSingleBean(DelegatingPrincipalCoffeeNetProfileMapper.class);
            assertThat(context).hasSingleBean(CoffeeNetProfileArgumentResolverConfigurer.class);
        });
    }
}
