package coffee.synyx.autoconfigure.discovery.endpoint;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.core.Is.is;

import static org.mockito.Mockito.when;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetAppsEndpointTest {

    @Mock
    private CoffeeNetAppService coffeeNetAppServiceMock;

    @Test
    public void testDefaultValues() {

        CoffeeNetAppsEndpoint sut = new CoffeeNetAppsEndpoint(coffeeNetAppServiceMock, emptySet());

        assertThat(sut.getId(), is("coffeenet/apps"));
        assertThat(sut.isEnabled(), is(true));
        assertThat(sut.isSensitive(), is(false));
    }


    @Test
    public void invokeWithEmptyUserRoles() {

        CoffeeNetAppsEndpoint sut = new CoffeeNetAppsEndpoint(coffeeNetAppServiceMock, emptySet());

        when(coffeeNetAppServiceMock.getApps()).thenReturn(asList(
                new CoffeeNetApp("NoRights", "urlNoRights", emptySet()),
                new CoffeeNetApp("OneRight", "urlAdminRights", roles("ROLE_COFFEENET-ADMIN")),
                new CoffeeNetApp("MultipleRights", "urlUserRights",
                    roles("ROLE_COFFEENET-USER", "ROLE_COFFEENET-ADMIN"))));

        List<CoffeeNetApp> filteredCoffeeNetAppList = sut.invoke();
        assertThat(filteredCoffeeNetAppList, hasSize(1));
        assertThat(filteredCoffeeNetAppList.get(0).getName(), is("NoRights"));
    }


    @Test
    public void invokeWithOneUserRoles() {

        HashSet<String> userRoles = roles("ROLE_COFFEENET-ADMIN");
        CoffeeNetAppsEndpoint sut = new CoffeeNetAppsEndpoint(coffeeNetAppServiceMock, userRoles);

        when(coffeeNetAppServiceMock.getApps()).thenReturn(asList(
                new CoffeeNetApp("NoRights", "urlNoRights", emptySet()),
                new CoffeeNetApp("OneRight", "urlAdminRights", roles("ROLE_COFFEENET-ADMIN")),
                new CoffeeNetApp("MultipleRights", "urlUserRights",
                    roles("ROLE_COFFEENET-USER", "ROLE_COFFEENET-ADMIN"))));

        List<CoffeeNetApp> filteredCoffeeNetAppList = sut.invoke();
        assertThat(filteredCoffeeNetAppList, hasSize(3));
        assertThat(filteredCoffeeNetAppList.get(0).getName(), is("NoRights"));
        assertThat(filteredCoffeeNetAppList.get(1).getName(), is("OneRight"));
        assertThat(filteredCoffeeNetAppList.get(2).getName(), is("MultipleRights"));
    }


    @Test
    public void invokeMultipleUserRoles() {

        HashSet<String> userRoles = roles("ROLE_COFFEENET-ADMIN", "ROLE_COFFEENET-SADMIN");
        CoffeeNetAppsEndpoint sut = new CoffeeNetAppsEndpoint(coffeeNetAppServiceMock, userRoles);

        when(coffeeNetAppServiceMock.getApps()).thenReturn(asList(
                new CoffeeNetApp("NoRights", "urlNoRights", emptySet()),
                new CoffeeNetApp("OneRight", "urlAdminRights", roles("ROLE_COFFEENET-ADMIN")),
                new CoffeeNetApp("MultipleRights", "urlUserRights",
                    roles("ROLE_COFFEENET-USER", "ROLE_COFFEENET-ADMIN")),
                new CoffeeNetApp("SAdminRight", "SAdminRight", roles("ROLE_COFFEENET-SADMIN"))));

        List<CoffeeNetApp> filteredCoffeeNetAppList = sut.invoke();
        assertThat(filteredCoffeeNetAppList, hasSize(4));
        assertThat(filteredCoffeeNetAppList.get(0).getName(), is("NoRights"));
        assertThat(filteredCoffeeNetAppList.get(1).getName(), is("OneRight"));
        assertThat(filteredCoffeeNetAppList.get(2).getName(), is("MultipleRights"));
        assertThat(filteredCoffeeNetAppList.get(3).getName(), is("SAdminRight"));
    }


    private HashSet<String> roles(String... roles) {

        return new HashSet<>(asList(roles));
    }
}
