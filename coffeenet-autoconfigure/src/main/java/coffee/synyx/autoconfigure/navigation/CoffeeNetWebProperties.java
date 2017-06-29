package coffee.synyx.autoconfigure.navigation;

import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.validation.annotation.Validated;


/**
 * Properties for the web starters.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
@Validated
@ConfigurationProperties("coffeenet.web")
public class CoffeeNetWebProperties {

    @NotEmpty(message = "Please provide the name of the profile application")
    private String profileServiceName = "profile";

    @NotEmpty(message = "Please provide the path to the logout functionality or stay forever in the CoffeeNet")
    private String logoutPath = "/logout";

    public String getProfileServiceName() {

        return profileServiceName;
    }


    public void setProfileServiceName(String profileServiceName) {

        this.profileServiceName = profileServiceName;
    }


    public String getLogoutPath() {

        return logoutPath;
    }


    public void setLogoutPath(String logoutPath) {

        this.logoutPath = logoutPath;
    }


    @Override
    public String toString() {

        return "CoffeeNetWebProperties{"
            + "profileServiceName='" + profileServiceName + '\''
            + ", logoutPath='" + logoutPath + '\'' + '}';
    }
}
