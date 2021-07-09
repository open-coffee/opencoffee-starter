package rocks.coffeenet.autoconfigure.security.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import rocks.coffeenet.autoconfigure.security.CoffeeNetSecurityAutoConfiguration;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName("DelegatingSecurityConfigurer")
class DelegatingSecurityConfigurerTests {

    private WebApplicationContextRunner contextRunner;

    @BeforeEach
    void setupWebApplicationContext() {

        contextRunner =
            new WebApplicationContextRunner().withConfiguration(AutoConfigurations.of(SecurityAutoConfiguration.class,
                    SecurityFilterAutoConfiguration.class, CoffeeNetSecurityAutoConfiguration.class));
    }


    @Test
    @DisplayName("should run custom CoffeeNet security configurer when bean present")
    @Disabled("False negative in SB 2.5, loading of DelegatingSecurityConfigurer fails to happen in testing")
    void customSecurityConfigurer() {

        contextRunner.withUserConfiguration(CustomConfigurer.class).run((context) ->
                assertThat(context).getBean(CustomConfigurer.class).isNotNull().satisfies(configurer ->
                        assertThat(configurer.customized).isTrue()));
    }


    @Test
    @DisplayName("should not run custom CoffeeNet security configurer when using websecurity defaults are disabled")
    void disabledWebSecurityDefaults() {

        contextRunner.withUserConfiguration(CustomConfigurer.class, NoDefaultsWebSecurity.class)
            .run((context) ->
                    assertThat(context).getBean(CustomConfigurer.class).isNotNull().satisfies(configurer ->
                            assertThat(configurer.customized).isFalse()));
    }

    @Configuration
    static class CustomConfigurer extends AbstractHttpConfigurer<CustomConfigurer, HttpSecurity>
        implements CoffeeNetSecurityConfigurer {

        private boolean customized = false;

        @Override
        public void init(HttpSecurity builder) throws Exception {

            customized = true;
        }
    }

    @Configuration
    @Order(1)
    static class NoDefaultsWebSecurity extends WebSecurityConfigurerAdapter {

        protected NoDefaultsWebSecurity() {

            super(true);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            // Just add some configurers, which would add other needed beans for a security filter chain
            http.exceptionHandling().and()
                .headers().and()
                .sessionManagement().and()
                .securityContext().and()
                .requestCache().and()
                .anonymous().and()
                .servletApi();
        }
    }
}
