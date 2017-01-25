package coffee.synyx.autoconfigure.security.service;

import org.junit.Test;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

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
    public void hasRoles() {

        CoffeeNetUserDetails coffeeNetUserDetails = new CoffeeNetUserDetailsStub();
        assertThat(coffeeNetUserDetails.hasRoles("COFFEENET-ADMIN", "COFFEENET-USER"), is(true));
    }


    @Test
    public void hasRolesNotAll() {

        CoffeeNetUserDetails coffeeNetUserDetails = new CoffeeNetUserDetailsStub();
        assertThat(coffeeNetUserDetails.hasRoles("COFFEENET-ADMIN", "COFFEENET-WRONG-USER"), is(false));
    }


    @Test
    public void hasRolesNot() {

        CoffeeNetUserDetails coffeeNetUserDetails = new CoffeeNetUserDetailsStub();
        assertThat(coffeeNetUserDetails.hasRoles("COFFEENET-WRONG-USER"), is(false));
    }


    @Test
    public void hasRolesNotAndEmptyRole() {

        CoffeeNetUserDetails coffeeNetUserDetails = new CoffeeNetUserDetailsStub();
        assertThat(coffeeNetUserDetails.hasRoles(), is(true));
    }


    @Test
    public void hasRolesProvidesEmpty() {

        CoffeeNetUserDetails coffeeNetUserDetails = new CoffeeNetUserDetailsStub();
        assertThat(coffeeNetUserDetails.hasRoles(), is(true));
    }


    @Test
    public void hasRolesHasEmpty() {

        CoffeeNetUserDetails coffeeNetUserDetails = new CoffeeNetUserDetailsEmptyStub();
        assertThat(coffeeNetUserDetails.hasRoles("COFFEENET-USER"), is(false));
    }


    @Test
    public void getAuthoritiesAsString() {

        CoffeeNetUserDetails coffeeNetUserDetails = new CoffeeNetUserDetailsStub();
        assertThat(coffeeNetUserDetails.getAuthoritiesAsString(),
            is(hasItems("ROLE_COFFEENET-ADMIN", "ROLE_COFFEENET-USER")));
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

            return asList(new SimpleGrantedAuthority("ROLE_COFFEENET-ADMIN"),
                    new SimpleGrantedAuthority("ROLE_COFFEENET-USER"));
        }


        @Override
        public boolean isCoffeeNetAdmin() {

            return true;
        }


        @Override
        public boolean isMachineUser() {

            return false;
        }
    }

    private class CoffeeNetUserDetailsEmptyStub implements CoffeeNetUserDetails {

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

            return Collections.emptyList();
        }


        @Override
        public boolean isCoffeeNetAdmin() {

            return true;
        }


        @Override
        public boolean isMachineUser() {

            return false;
        }
    }
}
