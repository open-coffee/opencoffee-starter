package coffee.synyx.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Global coffeenet configuration properties.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  Yannic Klem - klem@synyx.de
 */
@ConfigurationProperties(prefix = "coffeenet")
public class CoffeeNetConfigurationProperties {

    public static final String DEVELOPMENT = "development";
    public static final String INTEGRATION = "integration";

    public enum Profile {

        DEVELOPMENT,
        INTEGRATION;
    }

    private String applicationName;
    private String allowedAuthorities;
    private Profile profile = Profile.DEVELOPMENT;

    public String getApplicationName() {

        return applicationName;
    }


    public void setApplicationName(String applicationName) {

        this.applicationName = applicationName;
    }


    public String getAllowedAuthorities() {

        return allowedAuthorities;
    }


    public void setAllowedAuthorities(String allowedAuthorities) {

        this.allowedAuthorities = allowedAuthorities;
    }


    public Profile getProfile() {

        return profile;
    }


    public void setProfile(Profile profile) {

        this.profile = profile;
    }
}
