package coffee.synyx.autoconfigure.discovery.service;

import coffee.synyx.autoconfigure.security.user.CoffeeNetUserDetails;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;


/**
 * Application Dto to represent the registered CoffeeNet application from service discovery.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public final class App {

    private final String name;
    private final String url;
    private final Set<String> allowedAuthorities;

    public App(String name, String url, Set<String> allowedAuthorities) {

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


    public boolean isAllowedToAccessBy(CoffeeNetUserDetails coffeeNetUserDetails) {

        if (allowedAuthorities == null || allowedAuthorities.isEmpty()) {
            return true;
        }

        for (String authority : allowedAuthorities) {
            for (GrantedAuthority grantedAuthority : coffeeNetUserDetails.getAuthorities()) {
                if (authority.equalsIgnoreCase(grantedAuthority.getAuthority())) {
                    return true;
                }
            }
        }

        return false;
    }
}
