package coffee.synyx.autoconfigure.web;

import coffee.synyx.autoconfigure.discovery.service.AppQuery;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.security.service.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.service.HumanCoffeeNetUser;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.when;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetWebServiceWithDiscoveryAndSecurityTest {

    private CoffeeNetWebServiceWithDiscoveryAndSecurity sut;

    @Mock
    private CoffeeNetCurrentUserService coffeeNetCurrentUserServiceMock;
    @Mock
    private CoffeeNetAppService coffeeNetAppServiceMock;

    @Before
    public void setUp() throws Exception {

        sut = new CoffeeNetWebServiceWithDiscoveryAndSecurity(coffeeNetCurrentUserServiceMock, coffeeNetAppServiceMock,
                new CoffeeNetWebProperties());
    }


    @Test
    public void get() {

        String profileAppName = "profile";
        CoffeeNetApp profileApp = new CoffeeNetApp(profileAppName, "profile.coffeenet", emptySet());
        String coffeeAppName = "Coffee App";
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp(coffeeAppName, "coffeeapp.coffeenet", emptySet());
        String xoffeeAppName = "Xoffee App";
        CoffeeNetApp xoffeeNetApp = new CoffeeNetApp(xoffeeAppName, "xoffeeapp2.coffeenet", emptySet());
        CoffeeNetApp xoffeeNetApp2 = new CoffeeNetApp(xoffeeAppName, "xoffeeapp.coffeenet", emptySet());

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        apps.put(profileAppName, singletonList(profileApp));
        apps.put(coffeeAppName, singletonList(coffeeNetApp));
        apps.put(xoffeeAppName, asList(xoffeeNetApp, xoffeeNetApp2));
        when(coffeeNetAppServiceMock.getApps(any(AppQuery.class))).thenReturn(apps);

        HumanCoffeeNetUser user = new HumanCoffeeNetUser("username", "email", emptyList());
        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(user);

        CoffeeNetWeb coffeeNetWeb = sut.get();
        assertThat(coffeeNetWeb.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getEmail()).isSameAs("email");
        assertThat(coffeeNetWeb.getProfileApp()).isSameAs(profileApp);
        assertThat(coffeeNetWeb.getCoffeeNetApps()).hasSize(2);
        assertThat(coffeeNetWeb.getCoffeeNetApps().get(0)).isSameAs(coffeeNetApp);
        assertThat(coffeeNetWeb.getCoffeeNetApps().get(1)).isSameAs(xoffeeNetApp);
    }


    @Test
    public void getNoProfileApp() {

        when(coffeeNetAppServiceMock.getApps(any(AppQuery.class))).thenReturn(emptyMap());
        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(new HumanCoffeeNetUser("username", "email",
                emptyList()));

        CoffeeNetWeb coffeeNetWeb = sut.get();
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getEmail()).isSameAs("email");
        assertThat(coffeeNetWeb.getProfileApp()).isNull();
        assertThat(coffeeNetWeb.getLogoutPath()).isEqualTo("/logout");
    }


    @Test
    public void getWithoutUser() {

        when(coffeeNetAppServiceMock.getApps(any(AppQuery.class))).thenReturn(emptyMap());
        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(null);

        CoffeeNetWeb coffeeNetWeb = sut.get();
        assertThat(coffeeNetWeb.getCoffeeNetWebUser()).isNull();
        assertThat(coffeeNetWeb.getProfileApp()).isNull();
        assertThat(coffeeNetWeb.getLogoutPath()).isEqualTo("/logout");
    }
}
