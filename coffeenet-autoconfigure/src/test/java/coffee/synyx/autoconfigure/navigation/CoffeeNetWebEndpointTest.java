package coffee.synyx.autoconfigure.navigation;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;

import org.hamcrest.core.Is;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.core.Is.is;

import static org.mockito.Mockito.when;

import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetWebEndpointTest {

    @Mock
    private CoffeeNetWebService coffeeNetWebServiceMock;

    @Test
    public void testDefaultValues() {

        CoffeeNetWebEndpoint sut = new CoffeeNetWebEndpoint(coffeeNetWebServiceMock);

        assertThat(sut.getId(), is("coffeenet/web"));
        assertThat(sut.isEnabled(), is(true));
        assertThat(sut.isSensitive(), is(false));
    }


    @Test
    public void invokeWithEmptyUserRoles() {

        CoffeeNetWebUser coffeeNetWebUser = new CoffeeNetWebUser("username", "email");
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("NoRights", "urlNoRights", emptySet());
        List<CoffeeNetApp> coffeeNetApps = singletonList(coffeeNetApp);
        CoffeeNetWeb coffeeNetWeb = new CoffeeNetWeb(coffeeNetWebUser, coffeeNetApps, coffeeNetApp, "path");
        when(coffeeNetWebServiceMock.get()).thenReturn(coffeeNetWeb);

        CoffeeNetWebEndpoint sut = new CoffeeNetWebEndpoint(coffeeNetWebServiceMock);

        CoffeeNetWeb receivedCoffeeNetWeb = sut.invoke();
        assertThat(receivedCoffeeNetWeb.getCoffeeNetApps(), is(coffeeNetApps));
        assertThat(receivedCoffeeNetWeb.getCoffeeNetWebUser(), Is.is(coffeeNetWebUser));
        assertThat(receivedCoffeeNetWeb.getLogoutPath(), is("path"));
        assertThat(receivedCoffeeNetWeb.getProfileApp(), is(coffeeNetApp));
    }
}
