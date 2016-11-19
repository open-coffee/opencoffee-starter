package coffee.synyx.autoconfigure.security.config.integration;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;


/**
 * This filter should be used in the {@link org.springframework.security.web.SecurityFilterChain} to allow stateless
 * authentication with a bearer token in the Authorization-Header of a Request. If this Filter is not used
 * authentication is only possible with an active session.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
class ApiTokenAccessFilter extends OAuth2AuthenticationProcessingFilter {

    private final ResourceServerProperties resourceServerProperties;

    ApiTokenAccessFilter(ResourceServerTokenServices resourceServerTokenServices,
        ResourceServerProperties resourceServerProperties) {

        super();
        this.resourceServerProperties = resourceServerProperties;

        setStateless(false);
        setAuthenticationManager(oauthAuthenticationManager(resourceServerTokenServices));
    }

    private AuthenticationManager oauthAuthenticationManager(ResourceServerTokenServices tokenServices) {

        OAuth2AuthenticationManager oauthAuthenticationManager = new OAuth2AuthenticationManager();
        oauthAuthenticationManager.setResourceId(resourceServerProperties.getResourceId());
        oauthAuthenticationManager.setTokenServices(tokenServices);
        oauthAuthenticationManager.setClientDetailsService(null);

        return oauthAuthenticationManager;
    }
}
