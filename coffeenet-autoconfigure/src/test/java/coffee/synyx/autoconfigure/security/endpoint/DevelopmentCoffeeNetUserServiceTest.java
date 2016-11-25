package coffee.synyx.autoconfigure.security.endpoint;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class DevelopmentCoffeeNetUserServiceTest {

    private DevelopmentCoffeeNetUserService sut;

    @Before
    public void setUp() {

        sut = new DevelopmentCoffeeNetUserService();
    }


    @Test
    public void getUser() {

        CoffeeNetUser user = sut.getUser();
        assertThat(user.getEmail(), is("Coffy@felinepredator.net"));
        assertThat(user.getUsername(), is("Coffy"));
    }
}
