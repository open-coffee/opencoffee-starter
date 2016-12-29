package coffee.synyx.autoconfigure.security.endpoint;

import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.user.HumanCoffeeNetUser;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;

import static org.mockito.Mockito.when;

import static java.util.Collections.emptyList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class DevelopmentCoffeeNetUserServiceTest {

    private DevelopmentCoffeeNetUserService sut;

    @Mock
    private CoffeeNetCurrentUserService coffeeNetCurrentUserServiceMock;

    @Before
    public void setUp() {

        sut = new DevelopmentCoffeeNetUserService(coffeeNetCurrentUserServiceMock);
    }


    @Test
    public void getUser() {

        String username = "user";
        String email = "email";
        HumanCoffeeNetUser user = new HumanCoffeeNetUser(username, email, emptyList());

        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(user);

        CoffeeNetUser coffeeNetUser = sut.getUser();
        assertThat(coffeeNetUser.getUsername(), is(username));
        assertThat(coffeeNetUser.getEmail(), is(email));
    }
}
