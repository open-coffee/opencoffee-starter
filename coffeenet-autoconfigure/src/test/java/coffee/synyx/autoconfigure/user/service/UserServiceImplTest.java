package coffee.synyx.autoconfigure.user.service;

import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.user.CoffeeNetUserDetails;
import coffee.synyx.autoconfigure.security.user.HumanCoffeeNetUser;

import org.junit.Test;

import java.util.HashSet;

import static org.hamcrest.Matchers.is;

import static org.junit.Assert.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @author  David Schilling - schilling@synyx.de
 */
public class UserServiceImplTest {

    private UserServiceImpl sut;

    private CoffeeNetCurrentUserService coffeeNetCurrentUserServiceMock = mock(CoffeeNetCurrentUserService.class);

    @Test
    public void getUser() {

        sut = new UserServiceImpl(coffeeNetCurrentUserServiceMock);

        CoffeeNetUserDetails coffeeNetUserDetails = new HumanCoffeeNetUser("foo", "foo@bar.de", new HashSet<>());
        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(coffeeNetUserDetails);

        User user = sut.getUser();

        assertThat(user.getUsername(), is("foo"));
        assertThat(user.getEmail(), is("foo@bar.de"));
    }
}
