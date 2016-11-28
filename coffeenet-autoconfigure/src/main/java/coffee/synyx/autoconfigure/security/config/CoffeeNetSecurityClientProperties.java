package coffee.synyx.autoconfigure.security.config;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@ConfigurationProperties("coffeenet.security.client")
public class CoffeeNetSecurityClientProperties extends AuthorizationCodeResourceDetails {

    @URL
    @NotBlank
    private String accessTokenUri = "http://localhost:9999/oauth/token";

    @URL
    @NotBlank
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
