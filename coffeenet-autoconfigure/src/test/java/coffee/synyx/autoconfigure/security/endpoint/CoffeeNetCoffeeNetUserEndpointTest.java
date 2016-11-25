package coffee.synyx.autoconfigure.security.endpoint;

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
public class CoffeeNetCoffeeNetUserEndpointTest {

    private CoffeeNetUserEndpoint sut;

    @Mock
    private CoffeeNetUserService coffeeNetUserServiceMock;

    @Before
    public void setUp() {

        sut = new CoffeeNetUserEndpoint(coffeeNetUserServiceMock);
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

        CoffeeNetUser franz = new CoffeeNetUser("Franz", "Ferdinant@coffeenet.org");
        when(coffeeNetUserServiceMock.getUser()).thenReturn(franz);

        assertThat(sut.invoke(), is(franz));
    }
}
