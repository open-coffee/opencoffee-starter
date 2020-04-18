package rocks.coffeenet.legacy.autoconfigure.navigation;

import org.assertj.core.api.Assertions;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import rocks.coffeenet.legacy.autoconfigure.discovery.service.CoffeeNetApp;

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
public class CoffeeNetNavigationServiceImplTest {

    private CoffeeNetNavigationServiceImpl sut;

    @Mock
    private CoffeeNetNavigationDataExtractor dataExtractorMock;

    @Before
    public void setUp() {

        sut = new CoffeeNetNavigationServiceImpl(dataExtractorMock);
    }


    @Test
    public void get() {

        Optional<CurrentCoffeeNetUser> coffeeNetWebUser = Optional.of(new CurrentCoffeeNetUser("username", "email"));
        when(dataExtractorMock.extractUser()).thenReturn(coffeeNetWebUser);

        Optional<CoffeeNetNavigationAppInformation> coffeeNetNavigationAppInformation = Optional.of(
                new CoffeeNetNavigationAppInformation("groupId", "artifactId", "0.1.0", "parent-groupId",
                    "parent-artifactId", "0.2.0"));
        when(dataExtractorMock.extractAppInformation()).thenReturn(coffeeNetNavigationAppInformation);

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        apps.put("apps", singletonList(coffeeNetApp));

        CoffeeNetApp profileApp = new CoffeeNetApp("Profile", "profile.coffeenet", emptySet());
        apps.put("profile", singletonList(profileApp));
        when(dataExtractorMock.extractApps()).thenReturn(Optional.of(apps));

        when(dataExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetNavigationInformation coffeeNetNavigationInformation = sut.get();
        assertThat(coffeeNetNavigationInformation.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser().getEmail()).isSameAs("email");
        Assertions.assertThat(coffeeNetNavigationInformation.getProfileApp()).isSameAs(profileApp);
        assertThat(coffeeNetNavigationInformation.getCoffeeNetApps()).hasSize(1);
        Assertions.assertThat(coffeeNetNavigationInformation.getCoffeeNetApps().get(0)).isSameAs(coffeeNetApp);
        assertThat(coffeeNetNavigationInformation.getCoffeeNetNavigationAppInformation()).isSameAs(
            coffeeNetNavigationAppInformation.get());
    }


    @Test
    public void getNoUser() {

        when(dataExtractorMock.extractUser()).thenReturn(Optional.empty());

        Optional<CoffeeNetNavigationAppInformation> coffeeNetNavigationAppInformation = Optional.of(
                new CoffeeNetNavigationAppInformation("groupId", "artifactId", "0.1.0", "parent-groupId",
                    "parent-artifactId", "0.2.0"));
        when(dataExtractorMock.extractAppInformation()).thenReturn(coffeeNetNavigationAppInformation);

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        apps.put("apps", singletonList(coffeeNetApp));

        CoffeeNetApp profileApp = new CoffeeNetApp("Profile", "profile.coffeenet", emptySet());
        apps.put("profile", singletonList(profileApp));
        when(dataExtractorMock.extractApps()).thenReturn(Optional.of(apps));

        when(dataExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetNavigationInformation coffeeNetNavigationInformation = sut.get();
        assertThat(coffeeNetNavigationInformation.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser()).isNull();
        Assertions.assertThat(coffeeNetNavigationInformation.getProfileApp()).isSameAs(profileApp);
        assertThat(coffeeNetNavigationInformation.getCoffeeNetApps()).hasSize(1);
        Assertions.assertThat(coffeeNetNavigationInformation.getCoffeeNetApps().get(0)).isSameAs(coffeeNetApp);
        assertThat(coffeeNetNavigationInformation.getCoffeeNetNavigationAppInformation()).isSameAs(
            coffeeNetNavigationAppInformation.get());
    }


    @Test
    public void getNoApps() {

        Optional<CoffeeNetNavigationAppInformation> coffeeNetNavigationAppInformation = Optional.of(
                new CoffeeNetNavigationAppInformation("groupId", "artifactId", "0.1.0", "parent-groupId",
                    "parent-artifactId", "0.2.0"));
        when(dataExtractorMock.extractAppInformation()).thenReturn(coffeeNetNavigationAppInformation);

        Optional<CurrentCoffeeNetUser> coffeeNetWebUser = Optional.of(new CurrentCoffeeNetUser("username", "email"));
        when(dataExtractorMock.extractUser()).thenReturn(coffeeNetWebUser);

        when(dataExtractorMock.extractApps()).thenReturn(Optional.empty());
        when(dataExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetNavigationInformation coffeeNetNavigationInformation = sut.get();
        assertThat(coffeeNetNavigationInformation.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser().getEmail()).isSameAs("email");
        Assertions.assertThat(coffeeNetNavigationInformation.getProfileApp()).isNull();
        assertThat(coffeeNetNavigationInformation.getCoffeeNetApps()).isNull();
        assertThat(coffeeNetNavigationInformation.getCoffeeNetNavigationAppInformation()).isSameAs(
            coffeeNetNavigationAppInformation.get());
    }


    @Test
    public void getNoProfileApp() {

        Optional<CoffeeNetNavigationAppInformation> coffeeNetNavigationAppInformation = Optional.of(
                new CoffeeNetNavigationAppInformation("groupId", "artifactId", "0.1.0", "parent-groupId",
                    "parent-artifactId", "0.2.0"));
        when(dataExtractorMock.extractAppInformation()).thenReturn(coffeeNetNavigationAppInformation);

        Optional<CurrentCoffeeNetUser> coffeeNetWebUser = Optional.of(new CurrentCoffeeNetUser("username", "email"));
        when(dataExtractorMock.extractUser()).thenReturn(coffeeNetWebUser);

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        apps.put("apps", singletonList(coffeeNetApp));
        when(dataExtractorMock.extractApps()).thenReturn(Optional.of(apps));

        when(dataExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetNavigationInformation coffeeNetNavigationInformation = sut.get();
        assertThat(coffeeNetNavigationInformation.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser().getEmail()).isSameAs("email");
        Assertions.assertThat(coffeeNetNavigationInformation.getProfileApp()).isNull();
        assertThat(coffeeNetNavigationInformation.getCoffeeNetApps()).hasSize(1);
        Assertions.assertThat(coffeeNetNavigationInformation.getCoffeeNetApps().get(0)).isSameAs(coffeeNetApp);
        assertThat(coffeeNetNavigationInformation.getCoffeeNetNavigationAppInformation()).isSameAs(
            coffeeNetNavigationAppInformation.get());
    }


    @Test
    public void getNoVersion() {

        Optional<CurrentCoffeeNetUser> coffeeNetWebUser = Optional.of(new CurrentCoffeeNetUser("username", "email"));
        when(dataExtractorMock.extractUser()).thenReturn(coffeeNetWebUser);

        Optional<CoffeeNetNavigationAppInformation> coffeeNetNavigationAppInformation = Optional.empty();
        when(dataExtractorMock.extractAppInformation()).thenReturn(coffeeNetNavigationAppInformation);

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        apps.put("apps", singletonList(coffeeNetApp));

        CoffeeNetApp profileApp = new CoffeeNetApp("Profile", "profile.coffeenet", emptySet());
        apps.put("profile", singletonList(profileApp));
        when(dataExtractorMock.extractApps()).thenReturn(Optional.of(apps));

        when(dataExtractorMock.extractLogoutPath()).thenReturn("/logout");

        CoffeeNetNavigationInformation coffeeNetNavigationInformation = sut.get();
        assertThat(coffeeNetNavigationInformation.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetNavigationInformation.getCurrentCoffeeNetUser().getEmail()).isSameAs("email");
        Assertions.assertThat(coffeeNetNavigationInformation.getProfileApp()).isSameAs(profileApp);
        assertThat(coffeeNetNavigationInformation.getCoffeeNetApps()).hasSize(1);
        Assertions.assertThat(coffeeNetNavigationInformation.getCoffeeNetApps().get(0)).isSameAs(coffeeNetApp);
        assertThat(coffeeNetNavigationInformation.getCoffeeNetNavigationAppInformation()).isNull();
    }
}
