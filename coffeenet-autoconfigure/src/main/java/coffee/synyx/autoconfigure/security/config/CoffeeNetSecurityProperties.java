package coffee.synyx.autoconfigure.security.config;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  Yannic Klem - klem@synyx.de
 */
@ConfigurationProperties("coffeenet.security")
public class CoffeeNetSecurityProperties {

    @URL(message = "Please provide a valid url to your oauth logout endpoint")
    @NotBlank(message = "Please provide the logout endpoint of the oauth server usually ending with /logout")
    private String logoutSuccessUrl = "http://localhost:9999/logout";

    /**
     * If a default login success url is specified a user always will be redirected to this url after a successful
     * login. If not the user will be redirected to the url he/she visited before he/she got redirected to /login.
     */
    private String defaultLoginSuccessUrl;

    public String getLogoutSuccessUrl() {

        return logoutSuccessUrl;
    }


    public void setLogoutSuccessUrl(String logoutSuccessUrl) {

        this.logoutSuccessUrl = logoutSuccessUrl;
    }


    public String getDefaultLoginSuccessUrl() {

        return defaultLoginSuccessUrl;
    }


    public void setDefaultLoginSuccessUrl(String defaultLoginSuccessUrl) {

        this.defaultLoginSuccessUrl = defaultLoginSuccessUrl;
    }
}
