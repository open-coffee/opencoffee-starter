package rocks.coffeenet.legacy.autoconfigure.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.util.Assert;

import java.util.Collection;


/**
 * Human user implementation for {@link CoffeeNetUserDetails}.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  David Schilling - schilling@synyx.de
 */
public final class HumanCoffeeNetUser extends AbstractCoffeeNetUser {

    private final String email;
    private final UserDetailsInstance userDetailsInstance;

    /**
     * @param  username  Must not be null.
     * @param  email  Must not be null.
     * @param  authorities  Must not be null.
     */
    public HumanCoffeeNetUser(String username, String email, Collection<GrantedAuthority> authorities) {

        Assert.notNull(username, "Please provide a username");
        Assert.notNull(email, "Please provide a email");
        Assert.notNull(authorities, "Please provide authorities");

        this.email = email;
        this.userDetailsInstance = new UserDetailsInstance(username, authorities);
    }

    @Override
    public String getUsername() {

        return userDetailsInstance.getUsername();
    }


    @Override
    public String getEmail() {

        return email;
    }


    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        return userDetailsInstance.getAuthorities();
    }


    @Override
    public boolean isCoffeeNetAdmin() {

        return getAuthorities().contains(new SimpleGrantedAuthority("ROLE_COFFEENET-ADMIN"));
    }


    @Override
    public boolean isMachineUser() {

        return false;
    }


    @Override
    public String toString() {

        return "HumanCoffeeNetUser{"
            + "email='" + email + '\''
            + ", userDetailsInstance=" + userDetailsInstance + '}';
    }
}
