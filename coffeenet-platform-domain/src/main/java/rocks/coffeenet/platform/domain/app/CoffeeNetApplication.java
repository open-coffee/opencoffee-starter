package rocks.coffeenet.platform.domain.app;

import java.net.URL;


/**
 * General interface describing an user-facing application in the CoffeeNet platform.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
public interface CoffeeNetApplication {

    static Builder withNameAndApplicationUrl(String name, URL applicationUrl) {

        return new Builder(name, applicationUrl);
    }


    /**
     * The technical name of this CoffeeNet application. This MUST NOT return {@code null}.
     */
    String getName();


    /**
     * The human readable name for this CoffeeNet application. This MAY return {@code null}.
     *
     * <p>It is meant for UI display purposes and can be prepared for I18N according to external factors like browser
     * language headers or authenticated user.</p>
     */
    String getHumanReadableName();


    /**
     * The base URL of this CoffeeNet application. This MUST NOT return {@code null}.
     */
    URL getApplicationURL();


    /**
     * The icon URL of this CoffeeNet application. This MAY return {@code null}.
     */
    URL getIconURL();

    class Builder {

        private final String name;
        private final URL applicationUrl;

        private String humanReadableName;
        private URL iconUrl;

        public Builder(String name, URL applicationUrl) {

            this.name = name;
            this.applicationUrl = applicationUrl;
        }

        public Builder withHumanReadableName(String humanReadableName) {

            this.humanReadableName = humanReadableName;

            return this;
        }


        public Builder withIconUrl(URL iconUrl) {

            this.iconUrl = iconUrl;

            return this;
        }


        public CoffeeNetApplication build() {

            DefaultCoffeeNetApplication application = new DefaultCoffeeNetApplication(name, applicationUrl);
            application.setHumanReadableName(humanReadableName);
            application.setIconUrl(iconUrl);

            return application;
        }
    }
}
