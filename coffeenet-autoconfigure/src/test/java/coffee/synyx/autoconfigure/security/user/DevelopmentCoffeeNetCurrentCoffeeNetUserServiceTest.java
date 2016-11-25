package coffee.synyx.autoconfigure.security.user;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;

import static org.hamcrest.core.Is.is;


/**
 * @author  Tobias Schneider
 */
public class DevelopmentCoffeeNetCurrentCoffeeNetUserServiceTest {

    private DevelopmentCoffeeNetCurrentUserService sut;

    @Before
    public void setUp() {

        sut = new DevelopmentCoffeeNetCurrentUserService();
    }


    @Test
    public void get() {

        CoffeeNetUserDetails coffeeNetUserDetails = sut.get();
        assertThat(coffeeNetUserDetails, instanceOf(HumanCoffeeNetUser.class));
        assertThat(coffeeNetUserDetails.getPassword(), is(nullValue()));
        assertThat(coffeeNetUserDetails.getEmail(), is("development@coffeenet.org"));
        assertThat(coffeeNetUserDetails.getUsername(), is("development"));
        assertThat(coffeeNetUserDetails.getAuthorities(), contains(new SimpleGrantedAuthority("CoffeeNet-Admin")));
    }
}
