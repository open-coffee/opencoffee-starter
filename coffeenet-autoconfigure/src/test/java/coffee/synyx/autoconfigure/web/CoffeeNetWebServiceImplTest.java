package coffee.synyx.autoconfigure.web;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetWebServiceImplTest {

    private CoffeeNetWebServiceImpl sut;

    @Mock
    private CoffeeNetWebExtractor coffeeNetWebExtractorMock;

    @Before
    public void setUp() throws Exception {

        sut = new CoffeeNetWebServiceImpl(coffeeNetWebExtractorMock);
    }


    @Test
    public void get() {

        CoffeeNetApp profileApp = new CoffeeNetApp("profile", "profile.coffeenet", emptySet());
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        CoffeeNetApp xoffeeNetApp = new CoffeeNetApp("Xoffee App", "xoffeeapp2.coffeenet", emptySet());

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        apps.put("profile", singletonList(profileApp));
        apps.put("apps", asList(coffeeNetApp, xoffeeNetApp));
        when(coffeeNetWebExtractorMock.extractApps()).thenReturn(Optional.of(apps));

        CoffeeNetWebUser user = new CoffeeNetWebUser("username", "email");
        when(coffeeNetWebExtractorMock.extractUser()).thenReturn(Optional.of(user));

        when(coffeeNetWebExtractorMock.extractLogoutPath()).thenReturn("/logout");

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
    public void getWithoutProfileApp() {

        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        CoffeeNetApp xoffeeNetApp = new CoffeeNetApp("Xoffee App", "xoffeeapp2.coffeenet", emptySet());

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        apps.put("apps", asList(coffeeNetApp, xoffeeNetApp));
        when(coffeeNetWebExtractorMock.extractApps()).thenReturn(Optional.of(apps));

        CoffeeNetWebUser user = new CoffeeNetWebUser("username", "email");
        when(coffeeNetWebExtractorMock.extractUser()).thenReturn(Optional.of(user));

        when(coffeeNetWebExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetWeb coffeeNetWeb = sut.get();
        assertThat(coffeeNetWeb.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getEmail()).isSameAs("email");
        assertThat(coffeeNetWeb.getProfileApp()).isNull();
        assertThat(coffeeNetWeb.getCoffeeNetApps()).hasSize(2);
        assertThat(coffeeNetWeb.getCoffeeNetApps().get(0)).isSameAs(coffeeNetApp);
        assertThat(coffeeNetWeb.getCoffeeNetApps().get(1)).isSameAs(xoffeeNetApp);
    }


    @Test
    public void getWithoutUser() {

        CoffeeNetApp profileApp = new CoffeeNetApp("profile", "profile.coffeenet", emptySet());
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        CoffeeNetApp xoffeeNetApp = new CoffeeNetApp("Xoffee App", "xoffeeapp2.coffeenet", emptySet());

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        apps.put("profile", singletonList(profileApp));
        apps.put("apps", asList(coffeeNetApp, xoffeeNetApp));
        when(coffeeNetWebExtractorMock.extractApps()).thenReturn(Optional.of(apps));

        when(coffeeNetWebExtractorMock.extractUser()).thenReturn(Optional.empty());
        when(coffeeNetWebExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetWeb coffeeNetWeb = sut.get();
        assertThat(coffeeNetWeb.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser()).isNull();
        assertThat(coffeeNetWeb.getProfileApp()).isSameAs(profileApp);
        assertThat(coffeeNetWeb.getCoffeeNetApps()).hasSize(2);
        assertThat(coffeeNetWeb.getCoffeeNetApps().get(0)).isSameAs(coffeeNetApp);
        assertThat(coffeeNetWeb.getCoffeeNetApps().get(1)).isSameAs(xoffeeNetApp);
    }


    @Test
    public void getWithoutApps() {

        when(coffeeNetWebExtractorMock.extractApps()).thenReturn(Optional.empty());

        CoffeeNetWebUser user = new CoffeeNetWebUser("username", "email");
        when(coffeeNetWebExtractorMock.extractUser()).thenReturn(Optional.of(user));

        when(coffeeNetWebExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetWeb coffeeNetWeb = sut.get();
        assertThat(coffeeNetWeb.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getEmail()).isSameAs("email");
        assertThat(coffeeNetWeb.getProfileApp()).isNull();
        assertThat(coffeeNetWeb.getCoffeeNetApps()).isNull();
    }
}
