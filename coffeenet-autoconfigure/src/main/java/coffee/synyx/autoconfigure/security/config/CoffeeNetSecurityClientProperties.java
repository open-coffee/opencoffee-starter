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

    @NotBlank(message = "Please provide your CoffeeNet client id which has been deposited in the oauth server")
    private String clientId = "coffeeNetClient";

    @NotBlank(message = "Please provide your CoffeeNet client secret which has been deposited in the oauth server")
    private String clientSecret = "coffeeNetClientSecret";

    @URL(message = "Please provide a valid url to your oauth token endpoint")
    @NotBlank(message = "Please provide the token endpoint of the oauth server usually ending with /oauth/token")
    private String accessTokenUri = "http://localhost:9999/oauth/token";

    @URL(message = "Please provide a valid url to your oauth authorize endpoint")
    @NotBlank(
        message = "Please provide the authorize endpoint of the oauth server usually ending with /oauth/authorize"
    )
    private String userAuthorizationUri = "http://localhost:9999/oauth/authorize";

    @Override
    public String getClientId() {

        return clientId;
    }


    @Override
    public void setClientId(String clientId) {

        this.clientId = clientId;
    }


    @Override
    public String getClientSecret() {

        return clientSecret;
    }


    @Override
    public void setClientSecret(String clientSecret) {

        this.clientSecret = clientSecret;
    }


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
