package coffee.synyx.autoconfigure.user.endpoint;

import coffee.synyx.autoconfigure.user.service.User;
import coffee.synyx.autoconfigure.user.service.UserService;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.core.Is.is;

import static org.mockito.Mockito.when;


/**
 * @author  Tobias Schneider
 */
@RunWith(MockitoJUnitRunner.class)
public class UserEndpointTest {

    private UserEndpoint sut;

    @Mock
    private UserService userServiceMock;

    @Before
    public void setUp() {

        sut = new UserEndpoint(userServiceMock);
    }


    @Test
    public void getId() {

        assertThat(sut.getId(), is("coffeenet/user"));
    }


    @Test
    public void isEnabled() {

        assertThat(sut.isEnabled(), is(true));
    }


    @Test
    public void isSensitive() {

        assertThat(sut.isSensitive(), is(false));
    }


    @Test
    public void invoke() {

        User franz = new User("Franz", "Ferdinant@coffeenet.org");
        when(userServiceMock.getUser()).thenReturn(franz);

        assertThat(sut.invoke(), is(franz));
    }
}
