package rocks.coffeenet.legacy.autoconfigure.security.service;

import org.junit.Test;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.nullValue;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertThat;

import static java.util.Arrays.asList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class UserDetailsInstanceTest {

    @Test
    public void testDefaultValue() {

        Collection<GrantedAuthority> authorities = asList(new SimpleGrantedAuthority("ROLE_COFFEENET-ADMIN"),
                new SimpleGrantedAuthority("ROLE_COFFEENET-USER"));

        UserDetailsInstance userDetailsInstance = new UserDetailsInstance("username", authorities);
        assertThat(userDetailsInstance.getAuthorities(), is(authorities));
        assertThat(userDetailsInstance.getPassword(), is(nullValue()));
        assertThat(userDetailsInstance.getUsername(), is("username"));
        assertThat(userDetailsInstance.isAccountNonExpired(), is(true));
        assertThat(userDetailsInstance.isAccountNonLocked(), is(true));
        assertThat(userDetailsInstance.isCredentialsNonExpired(), is(true));
        assertThat(userDetailsInstance.isEnabled(), is(true));
    }
}
