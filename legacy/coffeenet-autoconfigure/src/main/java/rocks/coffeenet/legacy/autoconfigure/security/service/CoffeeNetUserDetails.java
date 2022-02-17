package rocks.coffeenet.legacy.autoconfigure.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Collection;
import java.util.Set;


/**
 * Extension of {@link UserDetails} is required for {@link OAuth2Authentication#getName()} to work.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  David Schilling - schilling@synyx.de
 * @since  0.21.0
 */
public interface CoffeeNetUserDetails {

    /**
     * Returns the username.
     *
     * @return  username
     */
    String getUsername();


    /**
     * Returns the Email.
     *
     * @return  email
     */
    String getEmail();


    /**
     * @return  {@code true} if user is a CoffeeNet admin, otherwise {@code false}
     */
    boolean isCoffeeNetAdmin();


    /**
     * @return  {@code true} if user is a machine admin, otherwise {@code false}
     */
    boolean isMachineUser();


    /**
     * Tests if the user has the provided roles.
     *
     * @param  roles  to
     *
     * @return  {@code true} if user has all of the roles, otherwise {@code false}
     */
    boolean hasRoles(String... roles);


    /**
     * Returns the authorities as a string set.
     *
     * @return  Set of authorities as string
     */
    Set<String> getAuthoritiesAsString();


    /**
     * @return  the authorities of this user
     */
    Collection<GrantedAuthority> getAuthorities();
}
