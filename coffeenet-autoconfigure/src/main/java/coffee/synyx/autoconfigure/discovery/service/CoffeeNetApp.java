package coffee.synyx.autoconfigure.discovery.service;

import java.util.Set;


/**
 * Application Dto to represent the registered CoffeeNet application from service discovery.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public final class CoffeeNetApp {

    private final String name;
    private final String url;
    private final Set<String> allowedAuthorities;

    public CoffeeNetApp(String name, String url, Set<String> allowedAuthorities) {

        this.name = name;
        this.url = url;
        this.allowedAuthorities = allowedAuthorities;
    }

    public String getName() {

        return name;
    }


    public String getUrl() {

        return url;
    }


    public Set<String> getAllowedAuthorities() {

        return allowedAuthorities;
    }


    public boolean isAllowedToAccessBy(Set<String> authorities) {

        if (allowedAuthorities == null || allowedAuthorities.isEmpty()) {
            return true;
        }

        for (String allowedAuthority : allowedAuthorities) {
            for (String authority : authorities) {
                if (allowedAuthority.equalsIgnoreCase(authority)) {
                    return true;
                }
            }
        }

        return false;
    }
}
