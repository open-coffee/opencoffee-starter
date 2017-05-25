package coffee.synyx.autoconfigure.security.service;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.util.Assert;

import java.util.Collection;

import static java.util.Collections.emptySet;


/**
 * Machine user implementation for {@link CoffeeNetUserDetails}.
 *
 * @author  David Schilling - schilling@synyx.de
 */
public final class MachineCoffeeNetUser implements CoffeeNetUserDetails {

    private static final long serialVersionUID = -2951903502295199963L;

    private final String username;
    private Collection<GrantedAuthority> authorities = emptySet();

    /**
     * @param  username  Must not be null.
     * @param  authorities  Must not be null.
     */
    public MachineCoffeeNetUser(String username, Collection<GrantedAuthority> authorities) {

        Assert.notNull(username, "Please provide a username");
        Assert.notNull(authorities, "Please provide authorities");

        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {

        return username;
    }


    @Override
    public String getEmail() {

        throw new UnsupportedOperationException();
    }


    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        return authorities;
    }


    @Override
    public boolean isCoffeeNetAdmin() {

        return false;
    }


    @Override
    public boolean isMachineUser() {

        return true;
    }
}
