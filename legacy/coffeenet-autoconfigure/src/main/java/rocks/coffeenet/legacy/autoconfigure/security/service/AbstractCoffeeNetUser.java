package rocks.coffeenet.legacy.autoconfigure.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.util.Assert;

import java.util.Set;

import static java.util.stream.Collectors.toSet;


/**
 * Abstract coffeenet user to provide default methods to the implementations.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.21.0
 */
abstract class AbstractCoffeeNetUser implements CoffeeNetUserDetails {

    @Override
    public boolean hasRoles(String... roles) {

        for (String role : roles) {
            Assert.isTrue(!role.startsWith("ROLE_"), role
                + " cannot start with ROLE_ (it is automatically added)");

            if (!getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + role))) {
                return false;
            }
        }

        return true;
    }


    @Override
    public Set<String> getAuthoritiesAsString() {

        return getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toSet());
    }
}
