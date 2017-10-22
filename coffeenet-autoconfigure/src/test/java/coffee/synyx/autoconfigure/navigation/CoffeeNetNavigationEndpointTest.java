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
public class CoffeeNetNavigationEndpointTest {

    @Mock
    private CoffeeNetNavigationService coffeeNetNavigationServiceMock;

//    @Test TODO
//    public void testDefaultValues() {
//
//        CoffeeNetNavigationEndpoint sut = new CoffeeNetNavigationEndpoint(coffeeNetNavigationServiceMock);
//
//        assertThat(sut.getId(), is("coffeenet/navigation"));
//    }

    @Test
    public void invokeWithEmptyUserRoles() {

        CurrentCoffeeNetUser currentCoffeeNetUser = new CurrentCoffeeNetUser("username", "email");
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("NoRights", "urlNoRights", emptySet());
        List<CoffeeNetApp> coffeeNetApps = singletonList(coffeeNetApp);
        CoffeeNetNavigationAppInformation coffeeNetNavigationAppInformation = new CoffeeNetNavigationAppInformation("",
                "", "", "", "", "");
        CoffeeNetNavigationInformation coffeeNetNavigationInformation = new CoffeeNetNavigationInformation(
                currentCoffeeNetUser, coffeeNetApps, coffeeNetApp, "path", coffeeNetNavigationAppInformation);
        when(coffeeNetNavigationServiceMock.get()).thenReturn(coffeeNetNavigationInformation);

        CoffeeNetNavigationEndpoint sut = new CoffeeNetNavigationEndpoint(coffeeNetNavigationServiceMock);

        CoffeeNetNavigationInformation receivedCoffeeNetNavigationInformation = sut.getNavigationInformation();
        assertThat(receivedCoffeeNetNavigationInformation.getCoffeeNetApps(), is(coffeeNetApps));
        assertThat(receivedCoffeeNetNavigationInformation.getCurrentCoffeeNetUser(), Is.is(currentCoffeeNetUser));
        assertThat(receivedCoffeeNetNavigationInformation.getLogoutPath(), is("path"));
        assertThat(receivedCoffeeNetNavigationInformation.getProfileApp(), is(coffeeNetApp));
    }
}
