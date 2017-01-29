package coffee.synyx.autoconfigure.discovery.service;

import java.util.HashSet;
import java.util.Set;


/**
 * Builder to query and filter {@link CoffeeNetApp}s registered in the discovery service.
 *
 * @author  Tobias Schneider
 */
public class AppQuery {

    private final Set<String> appNames;
    private final Set<String> appRoles;

    private AppQuery(Builder builder) {

        appNames = builder.appNames;
        appRoles = builder.appRoles;
    }

    public Set<String> getAppNames() {

        return appNames;
    }


    public Set<String> getAppRoles() {

        return appRoles;
    }


    public static Builder builder() {

        return new Builder();
    }

    public static class Builder {

        private final Set<String> appNames = new HashSet<>();
        private final Set<String> appRoles = new HashSet<>();

        public AppQuery build() {

            return new AppQuery(this);
        }


        public Builder withAppName(String appName) {

            appNames.add(appName);

            return this;
        }


        public Builder withRole(String role) {

            appRoles.add(role);

            return this;
        }


        public Builder withRoles(Set<String> roles) {

            appRoles.addAll(roles);

            return this;
        }
    }
}
