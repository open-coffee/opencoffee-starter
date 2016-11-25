package coffee.synyx.autoconfigure.security.user;

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

        String sysadmin = "ROLE_SYSADMIN";
        String cna = "ROLE_COFFEENET-ADMIN";

        List<GrantedAuthority> gAuth = asList(new SimpleGrantedAuthority(sysadmin), new SimpleGrantedAuthority(cna));
        HumanCoffeeNetUser sut = new HumanCoffeeNetUser("username", "email@coffee.net", gAuth);

        assertThat(sut.getUsername(), is("username"));
        assertThat(sut.getEmail(), is("email@coffee.net"));
        assertThat(sut.getAuthorities().contains(new SimpleGrantedAuthority(sysadmin)), is(true));
        assertThat(sut.getAuthorities().contains(new SimpleGrantedAuthority(cna)), is(true));
        assertThat(sut.isMachineUser(), is(false));
    }


    @Test
    public void isAdmin() {

        List<GrantedAuthority> authorities = singletonList(new SimpleGrantedAuthority("ROLE_SYSADMIN"));
        HumanCoffeeNetUser sut = new HumanCoffeeNetUser("username", "email@coffee.net", authorities);
        assertThat(sut.isAdmin(), is(true));
    }


    @Test
    public void isAdminNotAndEmptyAuthorities() {

        HumanCoffeeNetUser sut = new HumanCoffeeNetUser("username", "email@coffee.net", emptyList());
        assertThat(sut.isAdmin(), is(false));
    }


    @Test
    public void isAdminNotAndWrongAuthorities() {

        List<GrantedAuthority> authorities = singletonList(new SimpleGrantedAuthority("ROLE_NO"));
        HumanCoffeeNetUser sut = new HumanCoffeeNetUser("username", "email@coffee.net", authorities);
        assertThat(sut.isAdmin(), is(false));
    }
}
