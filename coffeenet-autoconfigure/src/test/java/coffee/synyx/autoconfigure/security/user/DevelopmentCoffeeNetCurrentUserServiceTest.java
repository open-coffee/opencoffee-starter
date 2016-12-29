package coffee.synyx.autoconfigure.security.user;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;

import static org.hamcrest.core.Is.is;

import static java.util.Collections.emptyList;


/**
 * @author  Tobias Schneider
 */
public class DevelopmentCoffeeNetCurrentUserServiceTest {

    private DevelopmentCoffeeNetCurrentUserService sut;

    @Before
    public void setUp() {

        sut = new DevelopmentCoffeeNetCurrentUserService();
    }


    @Test
    public void getAdmin() {

        providePrinciple(new HumanCoffeeNetUser("admin", "", emptyList()));

        CoffeeNetUserDetails coffeeNetUserDetails = sut.get();
        assertThat(coffeeNetUserDetails, instanceOf(HumanCoffeeNetUser.class));
        assertThat(coffeeNetUserDetails.getPassword(), is(nullValue()));
        assertThat(coffeeNetUserDetails.getEmail(), is("admin@coffeenet"));
        assertThat(coffeeNetUserDetails.getUsername(), is("admin"));
        assertThat(coffeeNetUserDetails.getAuthorities(), contains(new SimpleGrantedAuthority("COFFEENET-ADMIN")));
    }


    @Test
    public void getDefault() {

        providePrinciple(new HumanCoffeeNetUser("", "", emptyList()));

        CoffeeNetUserDetails coffeeNetUserDetails = sut.get();
        assertThat(coffeeNetUserDetails, instanceOf(HumanCoffeeNetUser.class));
        assertThat(coffeeNetUserDetails.getPassword(), is(nullValue()));
        assertThat(coffeeNetUserDetails.getEmail(), is("admin@coffeenet"));
        assertThat(coffeeNetUserDetails.getUsername(), is("admin"));
        assertThat(coffeeNetUserDetails.getAuthorities(), contains(new SimpleGrantedAuthority("COFFEENET-ADMIN")));
    }


    @Test
    public void getUser() {

        providePrinciple(new HumanCoffeeNetUser("user", "", emptyList()));

        CoffeeNetUserDetails coffeeNetUserDetails = sut.get();
        assertThat(coffeeNetUserDetails, instanceOf(HumanCoffeeNetUser.class));
        assertThat(coffeeNetUserDetails.getPassword(), is(nullValue()));
        assertThat(coffeeNetUserDetails.getEmail(), is("user@coffeenet"));
        assertThat(coffeeNetUserDetails.getUsername(), is("user"));
        assertThat(coffeeNetUserDetails.getAuthorities(), hasSize(0));
    }


    private void providePrinciple(CoffeeNetUserDetails principal) {

        SecurityContextImpl context = new SecurityContextImpl();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(principal, null));
        SecurityContextHolder.setContext(context);
    }
}
