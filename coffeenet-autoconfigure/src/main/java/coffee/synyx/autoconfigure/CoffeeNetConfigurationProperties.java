package coffee.synyx.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Global coffeenet configuration properties.
 *
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

    private Profile profile = Profile.DEVELOPMENT;

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
}
