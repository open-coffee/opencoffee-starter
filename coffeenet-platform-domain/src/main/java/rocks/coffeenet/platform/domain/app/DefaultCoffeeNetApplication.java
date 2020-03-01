package rocks.coffeenet.platform.domain.app;

import java.io.Serializable;

import java.net.URL;


/**
 * Simple default implementation of {@link CoffeeNetApplication}. Instances are created only via a builder pattern with
 * {@link DefaultCoffeeNetApplication.Builder}.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */

public class DefaultCoffeeNetApplication implements CoffeeNetApplication, Serializable {

    private final String name;
    private final URL applicationUrl;

    private String humanReadableName;
    private URL iconUrl;

    DefaultCoffeeNetApplication(String name, URL applicationUrl) {

        this.name = name;
        this.applicationUrl = applicationUrl;
    }

    public static Builder withNameAndApplicationUrl(String name, URL applicationUrl) {

        return new Builder(name, applicationUrl);
    }


    @Override
    public String getName() {

        return name;
    }


    @Override
    public URL getApplicationURL() {

        return applicationUrl;
    }


    @Override
    public String getHumanReadableName() {

        return humanReadableName;
    }


    void setHumanReadableName(String humanReadableName) {

        this.humanReadableName = humanReadableName;
    }


    @Override
    public URL getIconURL() {

        return iconUrl;
    }


    void setIconUrl(URL iconUrl) {

        this.iconUrl = iconUrl;
    }

    public static class Builder {

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
