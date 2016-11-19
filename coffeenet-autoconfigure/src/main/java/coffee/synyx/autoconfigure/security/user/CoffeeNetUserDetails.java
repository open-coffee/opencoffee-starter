package coffee.synyx.autoconfigure.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Collection;


/**
 * Extension of {@link UserDetails} is required for {@link OAuth2Authentication#getName()} to work.
 *
 * @author  David Schilling - schilling@synyx.de
 */
public interface CoffeeNetUserDetails extends UserDetails {

    @Override
    String getUsername();


    String getEmail();


    @Override
    Collection<? extends GrantedAuthority> getAuthorities();


    boolean isAdmin();


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
}
