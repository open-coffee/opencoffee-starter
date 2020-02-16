package rocks.coffeenet.legacy.autoconfigure.security.service;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;

import static org.hamcrest.core.Is.is;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;


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

        providePrinciple(new User("admin", "", singleton(new SimpleGrantedAuthority("ROLE_COFFEENET-ADMIN"))));

        CoffeeNetUserDetails coffeeNetUserDetails = sut.get().get();
        assertThat(coffeeNetUserDetails, instanceOf(HumanCoffeeNetUser.class));
        assertThat(coffeeNetUserDetails.getEmail(), is("admin@coffeenet"));
        assertThat(coffeeNetUserDetails.getUsername(), is("admin"));
        assertThat(coffeeNetUserDetails.getAuthorities(),
            contains(new SimpleGrantedAuthority("ROLE_COFFEENET-ADMIN")));
    }


    @Test
    public void getAdminWihMultipleRoles() {

        providePrinciple(new User("admin", "",
                asList(new SimpleGrantedAuthority("ROLE_COFFEENET-ADMIN"), new SimpleGrantedAuthority("ROLE_ADMIN"))));

        CoffeeNetUserDetails coffeeNetUserDetails = sut.get().get();
        assertThat(coffeeNetUserDetails, instanceOf(HumanCoffeeNetUser.class));
        assertThat(coffeeNetUserDetails.getEmail(), is("admin@coffeenet"));
        assertThat(coffeeNetUserDetails.getUsername(), is("admin"));
        assertThat(coffeeNetUserDetails.getAuthorities(),
            contains(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_COFFEENET-ADMIN")));
    }


    @Test
    public void getUser() {

        providePrinciple(new User("user", "", emptyList()));

        CoffeeNetUserDetails coffeeNetUserDetails = sut.get().get();
        assertThat(coffeeNetUserDetails, instanceOf(HumanCoffeeNetUser.class));
        assertThat(coffeeNetUserDetails.getEmail(), is("user@coffeenet"));
        assertThat(coffeeNetUserDetails.getUsername(), is("user"));
        assertThat(coffeeNetUserDetails.getAuthorities(), hasSize(0));
    }


    @Test
    public void emptyAuthentication() {

        Optional<CoffeeNetUserDetails> coffeeNetUserDetails = sut.get();
        assertThat(coffeeNetUserDetails.isPresent(), is(false));
    }


    private void providePrinciple(User principal) {

        SecurityContextImpl context = new SecurityContextImpl();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(principal, null));
        SecurityContextHolder.setContext(context);
    }
}
