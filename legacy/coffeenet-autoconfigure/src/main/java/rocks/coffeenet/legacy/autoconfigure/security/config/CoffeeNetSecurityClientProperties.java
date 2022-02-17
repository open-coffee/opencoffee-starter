package rocks.coffeenet.legacy.autoconfigure.security.config;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import org.springframework.validation.annotation.Validated;


/**
 * This configuration properties class defines all properties that are defined under the prefix
 * {@code coffeenet.security.client} to configure client information like the oauth/token or oauth/authorize paths.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Validated
@ConfigurationProperties("coffeenet.security.client")
public class CoffeeNetSecurityClientProperties extends AuthorizationCodeResourceDetails {

    @URL(message = "Please provide a valid url to your oauth token endpoint")
    @NotBlank(message = "Please provide the token endpoint of the oauth server usually ending with /oauth/token")
    private String accessTokenUri = "http://localhost:9999/oauth/token";

    @URL(message = "Please provide a valid url to your oauth authorize endpoint")
    @NotBlank(
        message = "Please provide the authorize endpoint of the oauth server usually ending with /oauth/authorize"
    )
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


    @Override
    public String toString() {

        return "CoffeeNetSecurityClientProperties{"
            + "accessTokenUri='" + accessTokenUri + '\''
            + ", userAuthorizationUri='" + userAuthorizationUri + '\'' + '}';
    }
}
