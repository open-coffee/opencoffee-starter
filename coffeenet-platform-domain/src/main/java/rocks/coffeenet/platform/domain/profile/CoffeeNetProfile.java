package rocks.coffeenet.platform.domain.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.net.URL;


/**
 * A general interface describing profile information on an authenticated actor in the CoffeeNet platform.
 *
 * <p>While this contains convenient methods for accessing information like the username, full name or avatar URLs,
 * these are not meant to be in any way identifying. For example {@link #getHumanReadableName()} &amp;
 * {@link #getName()} could both return a representation that anonymizes this principal.</p>
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@JsonSerialize(as = CoffeeNetProfile.class)
public interface CoffeeNetProfile {

    static Builder withUniqueIdentifierAndName(String uniqueIdentifier, String name) {

        return new Builder(uniqueIdentifier, name);
    }


    /**
     * An unique identifier of this CoffeeNet profile, derived from the authenticated principal. This MUST NOT return
     * {@code null}.
     *
     * <p>This should be implemented in a privacy-aware way, mapping the authentication information in a one-way
     * fashion, so the originating authentication cannot be inferred.</p>
     *
     * @return  a unique identifier for this profile.
     */
    @JsonProperty("id")
    String getUniqueIdentifier();


    /**
     * The name of this CoffeeNet profile. This MUST NOT return {@code null}.
     *
     * @return  a (possibly technical) name for this profile.
     */
    @JsonProperty("name")
    String getName();


    /**
     * The human readable name of this CoffeeNet profile. This MAY return {@code null}.
     *
     * @return  a human readable name for this profile, if present.
     */
    @JsonProperty("human_readable_name")
    String getHumanReadableName();


    /**
     * An URL to a resource representation of this CoffeeNet profile. This MAY return {@code null}.
     *
     * @return  an URL to a resource representing this profile, if present.
     */
    @JsonProperty("profile_url")
    URL getProfileURL();


    /**
     * An URL to an image of this CoffeeNet profile. This MAY return {@code null}.
     *
     * @return  an URL to an avatar image for this profile, if present.
     */
    @JsonProperty("picture_url")
    URL getPictureURL();


    /**
     * The email address of this CoffeeNet profile. This MAY return {@code null}.
     *
     * @return  an email address for this profile, if present.
     */
    @JsonProperty("email")
    String getEmail();

    class Builder {

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
