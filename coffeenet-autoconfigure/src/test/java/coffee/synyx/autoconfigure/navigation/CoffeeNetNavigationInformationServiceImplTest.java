package coffee.synyx.autoconfigure.navigation;

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
public class CoffeeNetNavigationInformationServiceImplTest {

    private CoffeeNetNavigationServiceImpl sut;

    @Mock
    private CoffeeNetNavigationDataExtractor coffeeNetNavigationDataExtractorMock;

    @Before
    public void setUp() throws Exception {

        sut = new CoffeeNetNavigationServiceImpl(coffeeNetNavigationDataExtractorMock);
    }


    @Test
    public void get() {

        Optional<CurrentCoffeeNetUser> coffeeNetWebUser = Optional.of(new CurrentCoffeeNetUser("username", "email"));
        when(coffeeNetNavigationDataExtractorMock.extractUser()).thenReturn(coffeeNetWebUser);

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        apps.put("apps", singletonList(coffeeNetApp));

        CoffeeNetApp profileApp = new CoffeeNetApp("Profile", "profile.coffeenet", emptySet());
        apps.put("profile", singletonList(profileApp));
        when(coffeeNetNavigationDataExtractorMock.extractApps()).thenReturn(Optional.of(apps));

        when(coffeeNetNavigationDataExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetNavigationInformation coffeeNetNavigationInformation = sut.get();
        assertThat(coffeeNetNavigationInformation.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser().getEmail()).isSameAs("email");
        assertThat(coffeeNetNavigationInformation.getProfileApp()).isSameAs(profileApp);
        assertThat(coffeeNetNavigationInformation.getCoffeeNetApps()).hasSize(1);
        assertThat(coffeeNetNavigationInformation.getCoffeeNetApps().get(0)).isSameAs(coffeeNetApp);
    }


    @Test
    public void getNoUser() {

        when(coffeeNetNavigationDataExtractorMock.extractUser()).thenReturn(Optional.empty());

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        apps.put("apps", singletonList(coffeeNetApp));

        CoffeeNetApp profileApp = new CoffeeNetApp("Profile", "profile.coffeenet", emptySet());
        apps.put("profile", singletonList(profileApp));
        when(coffeeNetNavigationDataExtractorMock.extractApps()).thenReturn(Optional.of(apps));

        when(coffeeNetNavigationDataExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetNavigationInformation coffeeNetNavigationInformation = sut.get();
        assertThat(coffeeNetNavigationInformation.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser()).isNull();
        assertThat(coffeeNetNavigationInformation.getProfileApp()).isSameAs(profileApp);
        assertThat(coffeeNetNavigationInformation.getCoffeeNetApps()).hasSize(1);
        assertThat(coffeeNetNavigationInformation.getCoffeeNetApps().get(0)).isSameAs(coffeeNetApp);
    }


    @Test
    public void getNoApps() {

        Optional<CurrentCoffeeNetUser> coffeeNetWebUser = Optional.of(new CurrentCoffeeNetUser("username", "email"));
        when(coffeeNetNavigationDataExtractorMock.extractUser()).thenReturn(coffeeNetWebUser);

        when(coffeeNetNavigationDataExtractorMock.extractApps()).thenReturn(Optional.empty());
        when(coffeeNetNavigationDataExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetNavigationInformation coffeeNetNavigationInformation = sut.get();
        assertThat(coffeeNetNavigationInformation.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser().getEmail()).isSameAs("email");
        assertThat(coffeeNetNavigationInformation.getProfileApp()).isNull();
        assertThat(coffeeNetNavigationInformation.getCoffeeNetApps()).isNull();
    }


    @Test
    public void getNoProfileApp() {

        Optional<CurrentCoffeeNetUser> coffeeNetWebUser = Optional.of(new CurrentCoffeeNetUser("username", "email"));
        when(coffeeNetNavigationDataExtractorMock.extractUser()).thenReturn(coffeeNetWebUser);

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        apps.put("apps", singletonList(coffeeNetApp));
        when(coffeeNetNavigationDataExtractorMock.extractApps()).thenReturn(Optional.of(apps));

        when(coffeeNetNavigationDataExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetNavigationInformation coffeeNetNavigationInformation = sut.get();
        assertThat(coffeeNetNavigationInformation.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser().getEmail()).isSameAs("email");
        assertThat(coffeeNetNavigationInformation.getProfileApp()).isNull();
        assertThat(coffeeNetNavigationInformation.getCoffeeNetApps()).hasSize(1);
        assertThat(coffeeNetNavigationInformation.getCoffeeNetApps().get(0)).isSameAs(coffeeNetApp);
    }
}
