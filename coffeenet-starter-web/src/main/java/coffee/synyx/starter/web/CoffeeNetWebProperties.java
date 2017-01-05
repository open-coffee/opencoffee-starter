package coffee.synyx.starter.web;

import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Properties for the web starters.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
@ConfigurationProperties("coffeenet.web")
class CoffeeNetWebProperties {

    @NotEmpty
    private String profileServiceName = "profile";

    @NotEmpty
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
}
