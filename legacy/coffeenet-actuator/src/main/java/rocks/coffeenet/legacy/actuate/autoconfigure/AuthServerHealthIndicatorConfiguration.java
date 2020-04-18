package rocks.coffeenet.legacy.actuate.autoconfigure;

import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;

import org.springframework.web.client.RestTemplate;

import rocks.coffeenet.legacy.actuate.AuthServerHealthIndicator;

import static org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION;

import static rocks.coffeenet.legacy.autoconfigure.CoffeeNetConfigurationProperties.INTEGRATION;


/**
 * Health indicator for auth server in environments using CoffeeNet security.
 */
@Configuration
@ConditionalOnClass({ OAuth2ClientContext.class, WebSecurityConfigurerAdapter.class })
@Conditional({ AuthServerHealthIndicatorConfiguration.OnSecurityAndIntegrationEnabled.class })
public class AuthServerHealthIndicatorConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "authServerHealthIndicator")
    public HealthIndicator authServerHealthIndicator(
        AuthServerHealthIndicatorConfigurationProperties authServerHealthIndicatorConfigurationProperties) {

        return new AuthServerHealthIndicator(new RestTemplate(),
                authServerHealthIndicatorConfigurationProperties.getHealthUri());
    }


    @Bean
    public AuthServerHealthIndicatorConfigurationProperties authServerHealthIndicatorConfigurationProperties() {

        return new AuthServerHealthIndicatorConfigurationProperties();
    }

    static class OnSecurityAndIntegrationEnabled extends AllNestedConditions {

        OnSecurityAndIntegrationEnabled() {

            super(PARSE_CONFIGURATION);
        }

        @ConditionalOnProperty(
            prefix = "coffeenet.security", name = "enabled", havingValue = "true", matchIfMissing = true
        )
        static class OnSecurityEnabled {
        }

        @ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = INTEGRATION)
        static class OnIntegrationProfileEnabled {
        }
    }
}
