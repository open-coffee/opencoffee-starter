package coffee.synyx.autoconfigure.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


/**
 * Extension of {@link UserDetails} is required for {@link OAuth2Authentication#getName()} to work.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  David Schilling - schilling@synyx.de
 */
public interface CoffeeNetUserDetails extends UserDetails {

    @Override
    String getUsername();


    String getEmail();


    @Override
    Collection<? extends GrantedAuthority> getAuthorities();


    default boolean hasRoles(String... roles) {

        for (String role : roles) {
            Assert.isTrue(!role.startsWith("ROLE_"), role
                + " cannot start with ROLE_ (it is automatically added)");

            if (!getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + role))) {
                return false;
            }
        }

        return true;
    }


    boolean isCoffeeNetAdmin();


    boolean isMachineUser();


    @Override
    default String getPassword() {

        return null;
    }


    @Override
    default boolean isAccountNonExpired() {

        return true;
    }


    @Override
    default boolean isAccountNonLocked() {

        return true;
    }


    @Override
    default boolean isCredentialsNonExpired() {

        return true;
    }


    @Override
    default boolean isEnabled() {

        return true;
    }


    default Set<String> getAuthoritiesAsString() {

        return getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toSet());
    }
}
