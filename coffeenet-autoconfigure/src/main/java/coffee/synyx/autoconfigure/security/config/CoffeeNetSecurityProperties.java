package coffee.synyx.autoconfigure.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@ConfigurationProperties("coffeenet.security")
public class CoffeeNetSecurityProperties {

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
