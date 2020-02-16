package rocks.coffeenet.legacy.autoconfigure.security.service;

import org.junit.Test;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertThat;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class MachineCoffeeNetUserTest {

    @Test
    public void testMachineUserProperties() {

        String coffeeNetAdmin = "ROLE_COFFEENET-ADMIN";
        List<GrantedAuthority> gAuth = singletonList(new SimpleGrantedAuthority(coffeeNetAdmin));
        MachineCoffeeNetUser sut = new MachineCoffeeNetUser("username", gAuth);

        assertThat(sut.getUsername(), is("username"));
        assertThat(sut.getAuthorities().contains(new SimpleGrantedAuthority(coffeeNetAdmin)), is(true));
        assertThat(sut.isCoffeeNetAdmin(), is(false));
        assertThat(sut.isMachineUser(), is(true));
    }


    @Test(expected = UnsupportedOperationException.class)
    public void getEmailUnsupportedOperationException() {

        MachineCoffeeNetUser sut = new MachineCoffeeNetUser("username", emptyList());
        sut.getEmail();
    }
}
