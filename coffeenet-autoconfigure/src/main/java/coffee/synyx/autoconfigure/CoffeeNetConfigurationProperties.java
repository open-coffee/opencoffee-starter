package coffee.synyx.autoconfigure;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;


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

    @NotNull
    private Profile profile = Profile.DEVELOPMENT;

    @NotBlank
    private String applicationName;

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


    public String getAllowedAuthorities() {

        return allowedAuthorities;
    }


    public void setAllowedAuthorities(String allowedAuthorities) {

        this.allowedAuthorities = allowedAuthorities;
    }
}
