package coffee.synyx.autoconfigure.security.service;

import org.junit.Test;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.hamcrest.Matchers.hasItems;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertThat;

import static java.util.Arrays.asList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetUserDetailsTest {

    @Test
    public void testDefaultValue() {

        CoffeeNetUserDetails coffeeNetUserDetails = new CoffeeNetUserDetailsStub();
        assertThat(coffeeNetUserDetails.isAccountNonExpired(), is(true));
        assertThat(coffeeNetUserDetails.isAccountNonLocked(), is(true));
        assertThat(coffeeNetUserDetails.isCredentialsNonExpired(), is(true));
        assertThat(coffeeNetUserDetails.isEnabled(), is(true));
    }


    @Test
    public void getAuthoritiesAsString() {

        CoffeeNetUserDetails coffeeNetUserDetails = new CoffeeNetUserDetailsStub();
        assertThat(coffeeNetUserDetails.getAuthoritiesAsString(), is(hasItems("COFFEENET-ADMIN", "COFFEENET-USER")));
    }

    private class CoffeeNetUserDetailsStub implements CoffeeNetUserDetails {

        @Override
        public String getUsername() {

            return null;
        }


        @Override
        public String getEmail() {

            return null;
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {

            return asList(new SimpleGrantedAuthority("COFFEENET-ADMIN"), new SimpleGrantedAuthority("COFFEENET-USER"));
        }


        @Override
        public boolean isAdmin() {

            return false;
        }


        @Override
        public boolean isMachineUser() {

            return false;
        }
    }
}
