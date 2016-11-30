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
    @NotBlank(message = "Please provide the id of your oauth resource")
    private String id = "oauth2-resource";

    /**
     * URI of the user endpoint.
     */
    @URL(message = "Please provide a valid url to your oauth user endpoint")
    @NotBlank(message = "Please provide the user endpoint of the oauth server usually ending with /user")
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
