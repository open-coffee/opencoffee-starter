package coffee.synyx.autoconfigure.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@ConfigurationProperties("coffeenet.security.client")
public class CoffeeNetSecurityClientProperties extends AuthorizationCodeResourceDetails {

    private String accessTokenUri = "http://localhost:9999/oauth/token";

    private String userAuthorizationUri = "http://localhost:9999/oauth/authorize";

    @Override
    public String getAccessTokenUri() {

        return accessTokenUri;
    }


    @Override
    public void setAccessTokenUri(String accessTokenUri) {

        this.accessTokenUri = accessTokenUri;
    }


    @Override
    public String getUserAuthorizationUri() {

        return userAuthorizationUri;
    }


    @Override
    public void setUserAuthorizationUri(String userAuthorizationUri) {

        this.userAuthorizationUri = userAuthorizationUri;
    }
}
