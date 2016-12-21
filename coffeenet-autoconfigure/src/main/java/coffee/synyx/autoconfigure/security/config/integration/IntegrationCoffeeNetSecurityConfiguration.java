package coffee.synyx.autoconfigure.security.config.integration;

import coffee.synyx.autoconfigure.security.config.CoffeeNetSecurityClientProperties;
import coffee.synyx.autoconfigure.security.config.CoffeeNetSecurityProperties;
import coffee.synyx.autoconfigure.security.config.CoffeeNetSecurityResourceProperties;
import coffee.synyx.autoconfigure.security.oauth.CoffeeNetAuthoritiesExtractor;
import coffee.synyx.autoconfigure.security.oauth.CoffeeNetPrincipalExtractor;
import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.user.IntegrationCoffeeNetCurrentUserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.INTEGRATION;


/**
 * Security configuration to provide integration.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@ConditionalOnClass(OAuth2ClientContext.class)
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = INTEGRATION)
@EnableConfigurationProperties(
    {
        CoffeeNetSecurityProperties.class, CoffeeNetSecurityClientProperties.class,
        CoffeeNetSecurityResourceProperties.class
    }
)
@EnableOAuth2Client
public class IntegrationCoffeeNetSecurityConfiguration {

    static final String LOGIN = "/login";
    private static final int OAUTH_CLIENT_CONTEXT_FILTER_ORDER = -100;

    private final CoffeeNetSecurityClientProperties oAuth2ProtectedResourceDetails;
    private final CoffeeNetSecurityResourceProperties coffeeNetSecurityResourceProperties;
    private final CoffeeNetSecurityProperties coffeeNetSecurityProperties;

    @Autowired
    public IntegrationCoffeeNetSecurityConfiguration(CoffeeNetSecurityClientProperties oAuth2ProtectedResourceDetails,
        CoffeeNetSecurityResourceProperties coffeeNetSecurityResourceProperties,
        CoffeeNetSecurityProperties coffeeNetSecurityProperties) {

        this.oAuth2ProtectedResourceDetails = oAuth2ProtectedResourceDetails;
        this.coffeeNetSecurityResourceProperties = coffeeNetSecurityResourceProperties;
        this.coffeeNetSecurityProperties = coffeeNetSecurityProperties;
    }

    @Bean
    @ConditionalOnMissingBean(FilterRegistrationBean.class)
    public FilterRegistrationBean coffeeNetOauth2ClientFilterRegistration(
        OAuth2ClientContextFilter oAuth2ClientContextFilter) {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(oAuth2ClientContextFilter);
        registration.setOrder(OAUTH_CLIENT_CONTEXT_FILTER_ORDER);

        return registration;
    }


    @Bean
    @ConditionalOnMissingBean(CoffeeNetCurrentUserService.class)
    public CoffeeNetCurrentUserService coffeeNetCurrentUserService() {

        return new IntegrationCoffeeNetCurrentUserService();
    }


    @Bean
    @ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
    public IntegrationCoffeeNetWebSecurityConfigurerAdapter integrationCoffeeNetWebSecurityConfigurerAdapter(
        UserInfoTokenServices userInfoTokenServices,
        OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationProcessingFilter) {

        return new IntegrationCoffeeNetWebSecurityConfigurerAdapter(userInfoTokenServices,
                coffeeNetSecurityResourceProperties, coffeeNetSecurityProperties,
                oAuth2ClientAuthenticationProcessingFilter);
    }


    @Bean
    @ConditionalOnMissingBean(OAuth2RestTemplate.class)
    public OAuth2RestTemplate coffeeNetUserInfoRestTemplate(OAuth2ClientContext oauth2ClientContext) {

        return new OAuth2RestTemplate(oAuth2ProtectedResourceDetails, oauth2ClientContext);
    }


    @Bean
    @ConditionalOnMissingBean(UserInfoTokenServices.class)
    public UserInfoTokenServices coffeeNetUserInfoTokenServices(OAuth2RestTemplate coffeeNetUserInfoRestTemplate,
        AuthoritiesExtractor authoritiesExtractor, PrincipalExtractor principalExtractor) {

        UserInfoTokenServices userInfoTokenServices = new UserInfoTokenServices(
                coffeeNetSecurityResourceProperties.getUserInfoUri(), oAuth2ProtectedResourceDetails.getClientId());

        userInfoTokenServices.setAuthoritiesExtractor(authoritiesExtractor);
        userInfoTokenServices.setPrincipalExtractor(principalExtractor);
        userInfoTokenServices.setRestTemplate(coffeeNetUserInfoRestTemplate);

        return userInfoTokenServices;
    }


    @Bean
    @ConditionalOnMissingBean(AuthoritiesExtractor.class)
    public static AuthoritiesExtractor coffeeNetAuthoritiesExtractor() {

        return new CoffeeNetAuthoritiesExtractor();
    }


    @Bean
    @ConditionalOnMissingBean(PrincipalExtractor.class)
    public static PrincipalExtractor coffeeNetPrincipalExtractor(AuthoritiesExtractor authoritiesExtractor) {

        return new CoffeeNetPrincipalExtractor(authoritiesExtractor);
    }


    @Bean
    @ConditionalOnMissingBean(OAuth2ClientAuthenticationProcessingFilter.class)
    public OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationProcessingFilter(
        OAuth2RestTemplate userInfoRestTemplate, UserInfoTokenServices userInfoTokenServices,
        AuthenticationSuccessHandler defaultLoginSuccessUrlHandler,
        AuthenticationFailureHandler defaultAuthenticationFailureHandler) {

        OAuth2ClientAuthenticationProcessingFilter oAuthFilter = new OAuth2ClientAuthenticationProcessingFilter(LOGIN);
        oAuthFilter.setRestTemplate(userInfoRestTemplate);
        oAuthFilter.setTokenServices(userInfoTokenServices);
        oAuthFilter.setAuthenticationSuccessHandler(defaultLoginSuccessUrlHandler);
        oAuthFilter.setAuthenticationFailureHandler(defaultAuthenticationFailureHandler);

        return oAuthFilter;
    }


    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    public AuthenticationSuccessHandler defaultLoginSuccessUrlHandler() {

        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();

        if (coffeeNetSecurityProperties.getDefaultLoginSuccessUrl() != null) {
            handler.setDefaultTargetUrl(coffeeNetSecurityProperties.getDefaultLoginSuccessUrl());
            handler.setAlwaysUseDefaultTargetUrl(true);
        }

        return handler;
    }


    @Bean
    @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
    public AuthenticationFailureHandler defaultAuthenticationFailureHandler() {

        SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

        if (coffeeNetSecurityProperties.getDefaultLoginFailureUrl() != null) {
            failureHandler.setDefaultFailureUrl(coffeeNetSecurityProperties.getDefaultLoginFailureUrl());
        }

        return failureHandler;
    }
}
