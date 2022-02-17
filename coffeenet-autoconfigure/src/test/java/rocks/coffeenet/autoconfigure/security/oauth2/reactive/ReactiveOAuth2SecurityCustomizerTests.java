package rocks.coffeenet.autoconfigure.security.oauth2.reactive;

import org.assertj.core.api.Condition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;

import org.springframework.security.oauth2.client.web.server.OAuth2AuthorizationRequestRedirectWebFilter;
import org.springframework.security.oauth2.client.web.server.authentication.OAuth2LoginAuthenticationWebFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import rocks.coffeenet.autoconfigure.security.CoffeeNetSecurityAutoConfiguration;

import rocks.coffeenet.autoconfigure.security.oauth2.OAuth2TextFixtures;
import rocks.coffeenet.test.app.reactive.ReactiveTestWebApplication;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName("ReactiveOAuth2SecurityCustomizer")
class ReactiveOAuth2SecurityCustomizerTests {

    private ReactiveWebApplicationContextRunner contextRunner;

    @BeforeEach
    void setupWebApplicationContext() {

        contextRunner = new ReactiveWebApplicationContextRunner().withConfiguration(AutoConfigurations.of(
                        OAuth2ClientAutoConfiguration.class, ReactiveSecurityAutoConfiguration.class,
                        ReactiveUserDetailsServiceAutoConfiguration.class, ReactiveOAuth2ClientAutoConfiguration.class,
                        CoffeeNetSecurityAutoConfiguration.class, ReactiveOAuth2SecurityAutoConfiguration.class))
                .withUserConfiguration(ReactiveTestWebApplication.class);
    }


    @Test
    @DisplayName("should enable OAuth2 security on the filter chain if OAuth2 clients registered")
    void withRegisteredOAuth2Clients() {

        //J-
        //@formatter:off
        contextRunner.withPropertyValues(OAuth2TextFixtures.OAUTH_GITHUB_PROPERTIES)
            .run(context ->
                assertThat(context)
                    .getBean(SecurityWebFilterChain.class)
                    .extracting(SecurityWebFilterChain::getWebFilters)
                    .extracting(Flux::collectList)
                    .extracting(Mono::block)
                    .asList()
                    .haveAtLeastOne(new Condition<>(OAuth2LoginAuthenticationWebFilter.class::isInstance, "OAuth2LoginAuthenticationWebFilter"))
                    .haveAtLeastOne(new Condition<>(OAuth2AuthorizationRequestRedirectWebFilter.class::isInstance, "OAuth2AuthorizationRequestRedirectWebFilter")));
        //@formatter:on
        //J+
    }


    @Test
    @DisplayName("should not enable OAuth2 security on the filter chain if OAuth2 no clients registered")
    void withoutRegisteredOAuth2Clients() {

        //J-
        //@formatter:off
        contextRunner.run(context ->
            assertThat(context)
                .getBean(SecurityWebFilterChain.class)
                .extracting(SecurityWebFilterChain::getWebFilters)
                .extracting(Flux::collectList)
                .extracting(Mono::block)
                .asList()
                .doesNotHave(new Condition<>(OAuth2LoginAuthenticationWebFilter.class::isInstance, "OAuth2LoginAuthenticationWebFilter"))
                .doesNotHave(new Condition<>(OAuth2AuthorizationRequestRedirectWebFilter.class::isInstance, "OAuth2AuthorizationRequestRedirectWebFilter")));
        //@formatter:on
        //J+
    }
}
