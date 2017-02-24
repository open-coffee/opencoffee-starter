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

        Optional<CoffeeNetWebUser> coffeeNetWebUser = Optional.of(new CoffeeNetWebUser("username", "email"));
        when(coffeeNetWebExtractorMock.extractUser()).thenReturn(coffeeNetWebUser);

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        apps.put("apps", singletonList(coffeeNetApp));

        CoffeeNetApp profileApp = new CoffeeNetApp("Profile", "profile.coffeenet", emptySet());
        apps.put("profile", singletonList(profileApp));
        when(coffeeNetWebExtractorMock.extractApps()).thenReturn(Optional.of(apps));

        when(coffeeNetWebExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetWeb coffeeNetWeb = sut.get();
        assertThat(coffeeNetWeb.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getEmail()).isSameAs("email");
        assertThat(coffeeNetWeb.getProfileApp()).isSameAs(profileApp);
        assertThat(coffeeNetWeb.getCoffeeNetApps()).hasSize(1);
        assertThat(coffeeNetWeb.getCoffeeNetApps().get(0)).isSameAs(coffeeNetApp);
    }


    @Test
    public void getNoUser() {

        when(coffeeNetWebExtractorMock.extractUser()).thenReturn(Optional.empty());

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        apps.put("apps", singletonList(coffeeNetApp));

        CoffeeNetApp profileApp = new CoffeeNetApp("Profile", "profile.coffeenet", emptySet());
        apps.put("profile", singletonList(profileApp));
        when(coffeeNetWebExtractorMock.extractApps()).thenReturn(Optional.of(apps));

        when(coffeeNetWebExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetWeb coffeeNetWeb = sut.get();
        assertThat(coffeeNetWeb.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser()).isNull();
        assertThat(coffeeNetWeb.getProfileApp()).isSameAs(profileApp);
        assertThat(coffeeNetWeb.getCoffeeNetApps()).hasSize(1);
        assertThat(coffeeNetWeb.getCoffeeNetApps().get(0)).isSameAs(coffeeNetApp);
    }


    @Test
    public void getNoApps() {

        Optional<CoffeeNetWebUser> coffeeNetWebUser = Optional.of(new CoffeeNetWebUser("username", "email"));
        when(coffeeNetWebExtractorMock.extractUser()).thenReturn(coffeeNetWebUser);

        when(coffeeNetWebExtractorMock.extractApps()).thenReturn(Optional.empty());
        when(coffeeNetWebExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetWeb coffeeNetWeb = sut.get();
        assertThat(coffeeNetWeb.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getEmail()).isSameAs("email");
        assertThat(coffeeNetWeb.getProfileApp()).isNull();
        assertThat(coffeeNetWeb.getCoffeeNetApps()).isNull();
    }


    @Test
    public void getNoProfileApp() {

        Optional<CoffeeNetWebUser> coffeeNetWebUser = Optional.of(new CoffeeNetWebUser("username", "email"));
        when(coffeeNetWebExtractorMock.extractUser()).thenReturn(coffeeNetWebUser);

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        apps.put("apps", singletonList(coffeeNetApp));
        when(coffeeNetWebExtractorMock.extractApps()).thenReturn(Optional.of(apps));

        when(coffeeNetWebExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetWeb coffeeNetWeb = sut.get();
        assertThat(coffeeNetWeb.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getEmail()).isSameAs("email");
        assertThat(coffeeNetWeb.getProfileApp()).isNull();
        assertThat(coffeeNetWeb.getCoffeeNetApps()).hasSize(1);
        assertThat(coffeeNetWeb.getCoffeeNetApps().get(0)).isSameAs(coffeeNetApp);
    }
}
