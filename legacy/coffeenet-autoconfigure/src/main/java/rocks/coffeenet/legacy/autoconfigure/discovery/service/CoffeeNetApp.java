package rocks.coffeenet.legacy.autoconfigure.discovery.service;

import java.util.Set;

import static java.util.Collections.emptySet;


/**
 * Application Dto to represent the registered CoffeeNet application from service discovery.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public final class CoffeeNetApp {

    private final String name;
    private final String url;
    private final Set<String> authorities;

    public CoffeeNetApp(String name, String url, Set<String> authorities) {

        this.name = name;
        this.url = url;
        this.authorities = authorities;
    }

    public String getName() {

        return name;
    }


    public String getUrl() {

        return url;
    }


    public Set<String> getAuthorities() {

        return authorities;
    }


    public boolean isAllowedToAccessBy(Set<String> userAuthorities) {

        if (authorities == null || authorities.isEmpty()) {
            return true;
        }

        Set<String> userAuthoritiesNN = userAuthorities;

        if (userAuthorities == null) {
            userAuthoritiesNN = emptySet();
        }

        boolean isAllowed = false;

        for (String allowedAuthority : this.authorities) {
            for (String authority : userAuthoritiesNN) {
                if (allowedAuthority.equalsIgnoreCase(authority)) {
                    isAllowed = true;

                    break;
                }
            }
        }

        return isAllowed;
    }
}
