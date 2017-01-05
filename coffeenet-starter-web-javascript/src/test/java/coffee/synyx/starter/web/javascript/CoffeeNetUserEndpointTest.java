package coffee.synyx.starter.web.javascript;

import coffee.synyx.starter.web.CoffeeNetWeb;
import coffee.synyx.starter.web.CoffeeNetWebService;
import coffee.synyx.starter.web.CoffeeNetWebUser;

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
public class CoffeeNetUserEndpointTest {

    private CoffeeNetUserEndpoint sut;

    @Mock
    private CoffeeNetWebService coffeeNetWebServiceMock;

    @Before
    public void setUp() {

        sut = new CoffeeNetUserEndpoint(coffeeNetWebServiceMock);
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

        CoffeeNetWebUser franz = new CoffeeNetWebUser("Franz", "Ferdinant@coffeenet.org");
        CoffeeNetWeb coffeeNetWeb = new CoffeeNetWeb(franz, null, null, null);
        when(coffeeNetWebServiceMock.get()).thenReturn(coffeeNetWeb);

        assertThat(sut.invoke(), is(franz));
    }
}
