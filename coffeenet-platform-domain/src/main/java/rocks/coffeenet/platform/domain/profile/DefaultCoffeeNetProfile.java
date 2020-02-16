package rocks.coffeenet.platform.domain.profile;

import java.net.URL;


/**
 * Simple default implementation of {@link CoffeeNetProfile}. Instances are created only via a builder pattern with
 * {@link Builder}.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class DefaultCoffeeNetProfile implements CoffeeNetProfile {

    private final String uniqueIdentifier;
    private final String name;

    private String humanReadableName;
    private URL profileURL;
    private URL pictureURL;
    private String email;

    private DefaultCoffeeNetProfile(String uniqueIdentifier, String name) {

        this.uniqueIdentifier = uniqueIdentifier;
        this.name = name;
    }

    public static Builder withUniqueIdentifierAndName(String uniqueIdentifier, String name) {

        return new Builder(uniqueIdentifier, name);
    }


    @Override
    public String getUniqueIdentifier() {

        return uniqueIdentifier;
    }


    @Override
    public String getName() {

        return name;
    }


    @Override
    public String getHumanReadableName() {

        return humanReadableName != null ? humanReadableName : getName();
    }


    void setHumanReadableName(String humanReadableName) {

        this.humanReadableName = humanReadableName;
    }


    @Override
    public URL getProfileURL() {

        return profileURL;
    }


    void setProfileURL(URL profileURL) {

        this.profileURL = profileURL;
    }


    @Override
    public URL getPictureURL() {

        return pictureURL;
    }


    void setPictureURL(URL pictureURL) {

        this.pictureURL = pictureURL;
    }


    @Override
    public String getEmail() {

        return email;
    }


    void setEmail(String email) {

        this.email = email;
    }

    public static class Builder {

        private final String uniqueIdentifier;
        private final String name;

        private String humanReadableName;
        private URL profileURL;
        private URL pictureURL;
        private String email;

        public Builder(String uniqueIdentifier, String name) {

            this.uniqueIdentifier = uniqueIdentifier;
            this.name = name;
        }

        public Builder withHumanReadableName(String humanReadableName) {

            this.humanReadableName = humanReadableName;

            return this;
        }


        public Builder withProfileURL(URL profileURL) {

            this.profileURL = profileURL;

            return this;
        }


        public Builder withPictureURL(URL pictureURL) {

            this.pictureURL = pictureURL;

            return this;
        }


        public Builder withEmail(String email) {

            this.email = email;

            return this;
        }


        public CoffeeNetProfile build() {

            DefaultCoffeeNetProfile profile = new DefaultCoffeeNetProfile(uniqueIdentifier, name);
            profile.setHumanReadableName(humanReadableName);
            profile.setProfileURL(profileURL);
            profile.setPictureURL(pictureURL);
            profile.setEmail(email);

            return profile;
        }
    }
}
