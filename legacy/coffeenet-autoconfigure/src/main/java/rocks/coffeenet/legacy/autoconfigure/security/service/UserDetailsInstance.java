package rocks.coffeenet.legacy.autoconfigure.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.util.Assert;

import java.util.Collection;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.21.0
 */
class UserDetailsInstance implements UserDetails {

    private static final long serialVersionUID = 3475227969680202370L;

    private final String username;
    private Collection<GrantedAuthority> authorities;

    /**
     * @param  username  Must not be null.
     * @param  authorities  Must not be null.
     */
    UserDetailsInstance(String username, Collection<GrantedAuthority> authorities) {

        Assert.notNull(username, "Please provide a username");
        Assert.notNull(authorities, "Please provide authorities");

        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        return authorities;
    }


    @Override
    public String getPassword() {

        return null;
    }


    @Override
    public String getUsername() {

        return username;
    }


    @Override
    public boolean isAccountNonExpired() {

        return true;
    }


    @Override
    public boolean isAccountNonLocked() {

        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }


    @Override
    public boolean isEnabled() {

        return true;
    }


    @Override
    public String toString() {

        return "UserDetailsInstance{"
            + "username='" + username + '\''
            + ", authorities=" + authorities + '}';
    }
}
