package rocks.coffeenet.autoconfigure.security.oauth2.servlet;

import org.assertj.core.api.Condition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import org.springframework.security.oauth2.client.web.OAuth2AuthorizationCodeGrantFilter;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.FilterChainProxy;

import rocks.coffeenet.autoconfigure.security.CoffeeNetSecurityAutoConfiguration;
import rocks.coffeenet.autoconfigure.security.oauth2.OAuth2TextFixtures;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName("OAuth2SecurityConfigurer")
class OAuth2SecurityConfigurerTests {

    private WebApplicationContextRunner contextRunner;

    @BeforeEach
    void setupWebApplicationContext() {

        contextRunner =
            new WebApplicationContextRunner().withConfiguration(AutoConfigurations.of(
                    OAuth2ClientAutoConfiguration.class, SecurityAutoConfiguration.class,
                    SecurityFilterAutoConfiguration.class, CoffeeNetSecurityAutoConfiguration.class,
                    OAuth2SecurityAutoConfiguration.class));
    }


    @Test
    @DisplayName("should enable OAuth2 security on the filter chain if OAuth2 clients registered")
    void withRegisteredOAuth2Clients() {

        //J-
        //@formatter:off
        contextRunner.withPropertyValues(OAuth2TextFixtures.OAUTH_GITHUB_PROPERTIES)
            .run(context ->
                    assertThat(context)
                        .getBean(FilterChainProxy.class)
                        .extracting(fcp -> fcp.getFilters("/"))
                        .asList()
                        .haveAtLeastOne(new Condition<>(OAuth2LoginAuthenticationFilter.class::isInstance,
                            "OAuth2LoginAuthenticationFilter"))
                        .haveAtLeastOne(new Condition<>(OAuth2AuthorizationCodeGrantFilter.class::isInstance,
                            "OAuth2AuthorizationCodeGrantFilter")));
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
                    .getBean(FilterChainProxy.class)
                    .extracting(fcp -> fcp.getFilters("/"))
                    .asList()
                    .doesNotHave(new Condition<>(OAuth2LoginAuthenticationFilter.class::isInstance,
                        "OAuth2LoginAuthenticationFilter"))
                    .doesNotHave(new Condition<>(OAuth2AuthorizationCodeGrantFilter.class::isInstance,
                        "OAuth2AuthorizationCodeGrantFilter")));
        //@formatter:on
        //J+
    }
}
