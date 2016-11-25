package coffee.synyx.autoconfigure.security.user;

import org.junit.Test;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertThat;


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

            return null;
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
