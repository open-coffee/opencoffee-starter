package rocks.coffeenet.autoconfigure.security.reactive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.web.server.ServerHttpSecurity;

import rocks.coffeenet.autoconfigure.security.CoffeeNetSecurityAutoConfiguration;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName("ReactiveServerHttpSecurityPostProcessor")
class ReactiveServerHttpSecurityPostProcessorTests {

    private ReactiveWebApplicationContextRunner contextRunner;

    @BeforeEach
    void setupWebApplicationContext() {

        contextRunner =
            new ReactiveWebApplicationContextRunner().withConfiguration(AutoConfigurations.of(
                    ReactiveSecurityAutoConfiguration.class, ReactiveUserDetailsServiceAutoConfiguration.class,
                    ReactiveCoffeeNetSecurityAutoConfiguration.class, CoffeeNetSecurityAutoConfiguration.class));
    }


    @Test
    @DisplayName("should run custom CoffeeNet security customizer when bean present")
    void customSecurityConfigurer() {

        contextRunner.withUserConfiguration(TestCustomizer.class)
            .run((context) ->
                    assertThat(context).getBean(TestCustomizer.class).isNotNull()
                    .satisfies(customizer -> assertThat(customizer.customized).isTrue()));
    }

    @Test
    @DisplayName("should error out on null argument to constructor")
    void nullArgumentToConstructor() {
        assertThatThrownBy(() -> new ReactiveServerHttpSecurityPostProcessor(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("must not be null");
    }

    @Test
    @DisplayName("should error out on null argument to constructor")
    void emptyArgumentToConstructor() {
        assertThatThrownBy(() -> new ReactiveServerHttpSecurityPostProcessor(Collections.emptyList()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("must not be empty");
    }

    @Configuration
    static class TestCustomizer implements ReactiveServerHttpSecurityCustomizer {

        private boolean customized = false;

        @Override
        public void customize(ServerHttpSecurity http) {

            customized = true;
        }
    }
}
