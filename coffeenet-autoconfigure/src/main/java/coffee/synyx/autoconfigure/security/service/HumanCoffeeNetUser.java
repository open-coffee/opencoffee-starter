package coffee.synyx.autoconfigure.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.util.Assert;

import java.util.Collection;

import static java.util.Collections.emptySet;


/**
 * Human user implementation for {@link CoffeeNetUserDetails}.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  David Schilling - schilling@synyx.de
 */
public final class HumanCoffeeNetUser implements CoffeeNetUserDetails {

    private static final long serialVersionUID = 3475117968680202370L;

    private final String username;
    private final String email;
    private Collection<GrantedAuthority> authorities = emptySet();

    /**
     * @param  username  Must not be null.
     * @param  email  Must not be null.
     * @param  authorities  Must not be null.
     */
    public HumanCoffeeNetUser(String username, String email, Collection<GrantedAuthority> authorities) {

        Assert.notNull(username, "Please provide a username");
        Assert.notNull(email, "Please provide a email");
        Assert.notNull(authorities, "Please provide authorities");

        this.username = username;
        this.email = email;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {

        return username;
    }


    @Override
    public String getEmail() {

        return email;
    }


    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        return authorities;
    }


    @Override
    public boolean isCoffeeNetAdmin() {

        return getAuthorities().contains(new SimpleGrantedAuthority("ROLE_COFFEENET-ADMIN"));
    }


    @Override
    public boolean isMachineUser() {

        return false;
    }
}
