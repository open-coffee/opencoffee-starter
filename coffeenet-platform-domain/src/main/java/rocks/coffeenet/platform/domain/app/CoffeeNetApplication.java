package rocks.coffeenet.platform.domain.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.net.URL;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;


/**
 * General interface describing an user-facing application in the CoffeeNet platform.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
@JsonSerialize(as = CoffeeNetApplication.class)
public interface CoffeeNetApplication {

    static Builder withNameAndApplicationUrl(String name, URL applicationUrl) {

        return new Builder(name, applicationUrl);
    }


    /**
     * The technical name of this CoffeeNet application. This MUST NOT return {@code null}.
     *
     * @return  a technical name.
     */
    @JsonProperty("name")
    String getName();


    /**
     * The human readable name for this CoffeeNet application. This MAY return {@code null}.
     *
     * <p>It is meant for UI display purposes and can be prepared for I18N according to external factors like browser
     * language headers or authenticated user.</p>
     *
     * @return  a human readable name, if present.
     */
    @JsonProperty("human_readable_name")
    String getHumanReadableName();


    /**
     * The base URL of this CoffeeNet application. This MUST NOT return {@code null}.
     *
     * @return  an URL to access this application.
     */
    @JsonProperty("application_url")
    URL getApplicationURL();


    /**
     * The icon URL of this CoffeeNet application. This MAY return {@code null}.
     *
     * @return  an URL to an icon for this application, if present.
     */
    @JsonProperty("icon_url")
    URL getIconURL();


    /**
     * The set of authorities (as a {@link String}) that are needed to access this CoffeeNet application.
     *
     * @return  a (possibly empty) set of authorities.
     *
     * @deprecated  2.0.0
     */
    @JsonProperty("authorities")
    @Deprecated
    Set<String> getAuthorities();

    class Builder {

        private final String name;
        private final URL applicationUrl;
        private final Set<String> authorities = new TreeSet<>();

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


        public Builder withAuthority(String authority) {

            this.authorities.add(authority);

            return this;
        }


        public Builder withAuthorities(String... authorities) {

            return withAuthorities(Arrays.asList(authorities));
        }


        public Builder withAuthorities(Collection<String> authorities) {

            for (String authority : Objects.requireNonNull(authorities)) {
                withAuthority(authority);
            }

            return this;
        }


        public CoffeeNetApplication build() {

            DefaultCoffeeNetApplication application = new DefaultCoffeeNetApplication(name, applicationUrl);
            application.setHumanReadableName(humanReadableName);
            application.setIconUrl(iconUrl);
            application.setAuthorities(authorities);

            return application;
        }
    }
}
