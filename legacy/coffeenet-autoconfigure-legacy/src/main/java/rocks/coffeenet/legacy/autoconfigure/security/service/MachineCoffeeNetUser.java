package rocks.coffeenet.legacy.autoconfigure.security.service;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.util.Assert;

import java.util.Collection;


/**
 * Machine user implementation for {@link CoffeeNetUserDetails}.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  David Schilling - schilling@synyx.de
 */
public final class MachineCoffeeNetUser extends AbstractCoffeeNetUser {

    private final UserDetailsInstance userDetailsInstance;

    /**
     * @param  username  Must not be null.
     * @param  authorities  Must not be null.
     */
    public MachineCoffeeNetUser(String username, Collection<GrantedAuthority> authorities) {

        Assert.notNull(username, "Please provide a username");
        Assert.notNull(authorities, "Please provide authorities");

        this.userDetailsInstance = new UserDetailsInstance(username, authorities);
    }

    @Override
    public String getUsername() {

        return userDetailsInstance.getUsername();
    }


    @Override
    public String getEmail() {

        throw new UnsupportedOperationException();
    }


    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        return userDetailsInstance.getAuthorities();
    }


    @Override
    public boolean isCoffeeNetAdmin() {

        return false;
    }


    @Override
    public boolean isMachineUser() {

        return true;
    }


    @Override
    public String toString() {

        return "MachineCoffeeNetUser{"
            + "userDetailsInstance=" + userDetailsInstance + '}';
    }
}
