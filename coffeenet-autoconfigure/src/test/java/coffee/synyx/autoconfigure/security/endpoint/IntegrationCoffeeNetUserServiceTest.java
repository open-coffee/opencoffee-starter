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
public class IntegrationCoffeeNetUserServiceTest {

    @Mock
    private CoffeeNetCurrentUserService coffeeNetCurrentUserServiceMock;

    private IntegrationCoffeeNetUserService sut;

    @Before
    public void setUp() {

        sut = new IntegrationCoffeeNetUserService(coffeeNetCurrentUserServiceMock);
    }


    @Test
    public void getUser() {

        String username = "username";
        String emailUsername = "emailUsername";
        HumanCoffeeNetUser user = new HumanCoffeeNetUser(username, emailUsername, emptyList());

        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(user);

        CoffeeNetUser coffeeNetUser = sut.getUser();
        assertThat(coffeeNetUser.getUsername(), is(username));
        assertThat(coffeeNetUser.getEmail(), is(emailUsername));
    }
}
