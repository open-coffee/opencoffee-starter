package rocks.coffeenet.legacy.autoconfigure.discovery.service;

import java.util.Set;
import java.util.TreeSet;

import static java.lang.String.CASE_INSENSITIVE_ORDER;


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

    /**
     * Returns a case insensitive {@link TreeSet} of application names that matches the query.
     *
     * @return  case sensitive set of application names
     */
    public Set<String> getAppNames() {

        return appNames;
    }


    /**
     * Returns a case insensitive {@link TreeSet} of application roles that matches the query.
     *
     * @return  case sensitive set of application roles
     */
    public Set<String> getAppRoles() {

        return appRoles;
    }


    public static Builder builder() {

        return new Builder();
    }

    public static class Builder {

        private final Set<String> appNames = new TreeSet<>(CASE_INSENSITIVE_ORDER);
        private final Set<String> appRoles = new TreeSet<>(CASE_INSENSITIVE_ORDER);

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
