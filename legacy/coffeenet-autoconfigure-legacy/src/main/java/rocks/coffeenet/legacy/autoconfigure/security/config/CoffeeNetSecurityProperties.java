package rocks.coffeenet.legacy.autoconfigure.security.config;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


/**
 * This configuration properties class defines all properties that are defined on the first level with the prefix
 * {@code coffeenet.security} for e.g. to enable or disable the security or configure logout, success and failure urls.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  Yannic Klem - klem@synyx.de
 */
@Validated
@ConfigurationProperties("coffeenet.security")
public class CoffeeNetSecurityProperties {

    @NotNull
    private boolean enabled = true;

    @URL(message = "Please provide a valid url to your oauth logout endpoint")
    @NotBlank(message = "Please provide the logout endpoint of the oauth server usually ending with /logout")
    private String logoutSuccessUrl = "http://localhost:9999/logout";

    /**
     * If a default login success url is specified a user always will be redirected to this url after a successful
     * login. If not the user will be redirected to the url he/she visited before he/she got redirected to /login.
     */
    private String defaultLoginSuccessUrl;

    /**
     * If a default login failure url is specified a user always will be redirected to this url after a failed login.
     * This is mostly needed when session of the client timed out while the user is on the login page. When the user
     * triggers the login and is redirected back to the client, there is no session and the authentication will fail.
     * This would end in a 401 white label error page. The redirect will hide this error from the user.
     */
    @NotBlank(
        message = "Please provide a default login failure url. The user will be redirected to this url on a failed"
            + " authentication. Otherwise the user would see a white label error page."
    )
    private String defaultLoginFailureUrl = "/";

    public boolean isEnabled() {

        return enabled;
    }


    public void setEnabled(boolean enabled) {

        this.enabled = enabled;
    }


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


    public String getDefaultLoginFailureUrl() {

        return defaultLoginFailureUrl;
    }


    public void setDefaultLoginFailureUrl(String defaultLoginFailureUrl) {

        this.defaultLoginFailureUrl = defaultLoginFailureUrl;
    }


    @Override
    public String toString() {

        return "CoffeeNetSecurityProperties{"
            + "enabled=" + enabled
            + ", logoutSuccessUrl='" + logoutSuccessUrl + '\''
            + ", defaultLoginSuccessUrl='" + defaultLoginSuccessUrl + '\''
            + ", defaultLoginFailureUrl='" + defaultLoginFailureUrl + '\'' + '}';
    }
}
