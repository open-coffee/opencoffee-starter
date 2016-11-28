package coffee.synyx.autoconfigure.security.config;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * CoffeeNet security resource properties.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@ConfigurationProperties("coffeenet.security.resource")
public class CoffeeNetSecurityResourceProperties extends ResourceServerProperties {

    /**
     * Identifier of the resource.
     */
    @NotBlank
    private String id = "oauth2-resource";

    /**
     * URI of the user endpoint.
     */
    @URL
    @NotBlank
    private String userInfoUri = "http://localhost:9999/user";

    @Override
    public String getId() {

        return id;
    }


    @Override
    public void setId(String id) {

        this.id = id;
    }


    @Override
    public String getUserInfoUri() {

        return userInfoUri;
    }


    @Override
    public void setUserInfoUri(String userInfoUri) {

        this.userInfoUri = userInfoUri;
    }
}
