package coffee.synyx.starter.web.javascript;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;

import coffee.synyx.starter.web.CoffeeNetWeb;
import coffee.synyx.starter.web.CoffeeNetWebService;
import coffee.synyx.starter.web.CoffeeNetWebUser;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.hasItem;

import static org.hamcrest.core.Is.is;

import static org.mockito.Mockito.when;

import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetAppsEndpointTest {

    @Mock
    private CoffeeNetWebService coffeeNetWebServiceMock;

    @Test
    public void testDefaultValues() {

        CoffeeNetAppsEndpoint sut = new CoffeeNetAppsEndpoint(coffeeNetWebServiceMock);

        assertThat(sut.getId(), is("coffeenet/apps"));
        assertThat(sut.isEnabled(), is(true));
        assertThat(sut.isSensitive(), is(false));
    }


    @Test
    public void invokeWithEmptyUserRoles() {

        CoffeeNetWebUser coffeeNetWebUser = new CoffeeNetWebUser("username", "email");
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("NoRights", "urlNoRights", emptySet());
        CoffeeNetWeb coffeeNetWeb = new CoffeeNetWeb(coffeeNetWebUser, singletonList(coffeeNetApp), coffeeNetApp, "");

        when(coffeeNetWebServiceMock.get()).thenReturn(coffeeNetWeb);

        CoffeeNetAppsEndpoint sut = new CoffeeNetAppsEndpoint(coffeeNetWebServiceMock);

        Collection<CoffeeNetApp> filteredCoffeeNetAppList = sut.invoke();
        assertThat(filteredCoffeeNetAppList, hasItem(coffeeNetApp));
    }
}
