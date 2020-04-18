package rocks.coffeenet.legacy.autoconfigure.navigation;

import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


/**
 * Properties for the navigation starters.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
@Validated
@ConfigurationProperties("coffeenet.navigation")
public class CoffeeNetNavigationProperties {

    @NotEmpty(message = "Please provide the name of the profile application")
    private String profileServiceName = "profile";

    @NotEmpty(message = "Please provide the path to the logout functionality or stay forever in the CoffeeNet")
    private String logoutPath = "/logout";

    @NotNull(message = "Please provide the information to show or not show the version number in the navigation")
    private boolean displayVersions = true;

    /**
     * Comma separated list of roles that are allowed to see this application in the navigation e.g.
     *
     * <pre>
       coffeenet.navigation:
        display-in-navigation-for-roles: ROLE_COFFEENET-ADMIN, ROLE_USER
     * </pre>
     */
    private String displayInNavigationForRoles;

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


    public boolean isDisplayVersions() {

        return displayVersions;
    }


    public void setDisplayVersions(boolean displayVersions) {

        this.displayVersions = displayVersions;
    }


    public String getDisplayInNavigationForRoles() {

        return displayInNavigationForRoles;
    }


    public void setDisplayInNavigationForRoles(String displayInNavigationForRoles) {

        this.displayInNavigationForRoles = displayInNavigationForRoles;
    }


    @Override
    public String toString() {

        return "CoffeeNetNavigationProperties{"
            + "profileServiceName='" + profileServiceName + '\''
            + ", logoutPath='" + logoutPath + '\''
            + ", displayVersions=" + displayVersions
            + ", displayInNavigationForRoles='" + displayInNavigationForRoles + '\'' + '}';
    }
}
