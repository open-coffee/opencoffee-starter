package rocks.coffeenet.legacy.autoconfigure;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.validation.annotation.Validated;

import rocks.coffeenet.legacy.autoconfigure.navigation.CoffeeNetNavigationProperties;

import javax.validation.constraints.NotNull;


/**
 * Global coffeenet configuration properties.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  Yannic Klem - klem@synyx.de
 */
@Validated
@ConfigurationProperties(prefix = "coffeenet")
public class CoffeeNetConfigurationProperties {

    public static final String DEVELOPMENT = "development";
    public static final String INTEGRATION = "integration";

    public enum Profile {

        DEVELOPMENT,
        INTEGRATION
    }

    @NotNull(message = "Please choose the profile or mode in which your CoffeeNet application should start.")
    private Profile profile = Profile.DEVELOPMENT;

    @NotBlank(
        message = "Please define the name of your application. "
            + "This will be used in the navigation bar e.g."
    )
    private String applicationName;

    /**
     * @deprecated  since 0.38.0 in favor of {@link CoffeeNetNavigationProperties}
     *              'coffeenet.navigation.displayForRoles'
     */
    @Deprecated
    private String allowedAuthorities;

    public Profile getProfile() {

        return profile;
    }


    public void setProfile(Profile profile) {

        this.profile = profile;
    }


    public String getApplicationName() {

        return applicationName;
    }


    public void setApplicationName(String applicationName) {

        this.applicationName = applicationName;
    }


    @Deprecated
    public String getAllowedAuthorities() {

        return allowedAuthorities;
    }


    @Deprecated
    public void setAllowedAuthorities(String allowedAuthorities) {

        this.allowedAuthorities = allowedAuthorities;
    }


    @Override
    public String toString() {

        return "CoffeeNetConfigurationProperties{"
            + "profile=" + profile
            + ", applicationName='" + applicationName + '\''
            + ", allowedAuthorities='" + allowedAuthorities + '\'' + '}';
    }
}
