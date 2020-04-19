package rocks.coffeenet.platform.domain.profile;

import java.io.Serializable;

import java.net.URL;


/**
 * Simple default implementation of {@link CoffeeNetProfile}. Instances are created only via a builder pattern with
 * {@link CoffeeNetProfile.Builder}.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
class DefaultCoffeeNetProfile implements CoffeeNetProfile, Serializable {

    private final String uniqueIdentifier;
    private final String name;

    private String humanReadableName;
    private URL profileURL;
    private URL pictureURL;
    private String email;

    DefaultCoffeeNetProfile(String uniqueIdentifier, String name) {

        this.uniqueIdentifier = uniqueIdentifier;
        this.name = name;
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
}
