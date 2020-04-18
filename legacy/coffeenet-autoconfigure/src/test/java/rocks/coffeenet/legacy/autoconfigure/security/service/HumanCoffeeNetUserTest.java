package rocks.coffeenet.legacy.autoconfigure.security.service;

import org.junit.Test;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertThat;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class HumanCoffeeNetUserTest {

    @Test
    public void testHumanUserProperties() {

        String coffeeNetUser = "ROLE_COFFEENET-USER";
        String coffeeNetAdmin = "ROLE_COFFEENET-ADMIN";

        List<GrantedAuthority> gAuth = asList(new SimpleGrantedAuthority(coffeeNetUser),
                new SimpleGrantedAuthority(coffeeNetAdmin));
        HumanCoffeeNetUser sut = new HumanCoffeeNetUser("username", "email@coffeenet", gAuth);

        assertThat(sut.getUsername(), is("username"));
        assertThat(sut.getEmail(), is("email@coffeenet"));
        assertThat(sut.getAuthorities().contains(new SimpleGrantedAuthority(coffeeNetUser)), is(true));
        assertThat(sut.getAuthorities().contains(new SimpleGrantedAuthority(coffeeNetAdmin)), is(true));
        assertThat(sut.isMachineUser(), is(false));
    }


    @Test
    public void isAdmin() {

        List<GrantedAuthority> authorities = singletonList(new SimpleGrantedAuthority("ROLE_COFFEENET-ADMIN"));
        HumanCoffeeNetUser sut = new HumanCoffeeNetUser("username", "email@coffeenet", authorities);
        assertThat(sut.isCoffeeNetAdmin(), is(true));
    }


    @Test
    public void isAdminNotAndEmptyAuthorities() {

        HumanCoffeeNetUser sut = new HumanCoffeeNetUser("username", "email@coffeenet", emptyList());
        assertThat(sut.isCoffeeNetAdmin(), is(false));
    }


    @Test
    public void isAdminNotAndWrongAuthorities() {

        List<GrantedAuthority> authorities = singletonList(new SimpleGrantedAuthority("ROLE_NO"));
        HumanCoffeeNetUser sut = new HumanCoffeeNetUser("username", "email@coffeenet", authorities);
        assertThat(sut.isCoffeeNetAdmin(), is(false));
    }
}
