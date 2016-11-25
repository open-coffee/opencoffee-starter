package coffee.synyx.autoconfigure.security.endpoint;

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
public class IntegrationCoffeeNetCoffeeNetUserServiceTest {

    private IntegrationCoffeeNetUserService sut;

    private CoffeeNetCurrentUserService coffeeNetCurrentUserServiceMock = mock(CoffeeNetCurrentUserService.class);

    @Test
    public void getUser() {

        sut = new IntegrationCoffeeNetUserService(coffeeNetCurrentUserServiceMock);

        CoffeeNetUserDetails coffeeNetUserDetails = new HumanCoffeeNetUser("foo", "foo@bar.de", new HashSet<>());
        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(coffeeNetUserDetails);

        CoffeeNetUser coffeeNetUser = sut.getUser();

        assertThat(coffeeNetUser.getUsername(), is("foo"));
        assertThat(coffeeNetUser.getEmail(), is("foo@bar.de"));
    }
}
