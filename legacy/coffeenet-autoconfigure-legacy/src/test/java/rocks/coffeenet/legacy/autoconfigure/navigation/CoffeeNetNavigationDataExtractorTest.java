package rocks.coffeenet.legacy.autoconfigure.navigation;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import rocks.coffeenet.legacy.autoconfigure.discovery.service.CoffeeNetApp;
import rocks.coffeenet.legacy.autoconfigure.discovery.service.CoffeeNetAppService;
import rocks.coffeenet.legacy.autoconfigure.security.service.CoffeeNetCurrentUserService;
import rocks.coffeenet.legacy.autoconfigure.security.service.CoffeeNetUserDetails;
import rocks.coffeenet.legacy.autoconfigure.security.service.HumanCoffeeNetUser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.when;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;
import static java.util.Optional.of;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetNavigationDataExtractorTest {

    private CoffeeNetNavigationDataExtractor sut;

    @Mock
    private CoffeeNetAppService coffeeNetAppServiceMock;
    @Mock
    private CoffeeNetCurrentUserService coffeeNetCurrentUserServiceMock;

    @Before
    public void setUp() {

        sut = new CoffeeNetNavigationDataExtractor(new CoffeeNetNavigationProperties());
    }


    @Test
    public void extractApps() {

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        apps.put("cna1", singletonList(coffeeNetApp));

        CoffeeNetApp profileApp = new CoffeeNetApp("Profile", "profile.coffeenet", emptySet());
        apps.put("profile", singletonList(profileApp));

        sut.registerService(CoffeeNetNavigationDataExtractor.CoffeeNetServices.APP_SERVICE, coffeeNetAppServiceMock);
        when(coffeeNetAppServiceMock.getApps(any())).thenReturn(apps);

        // user
        sut.registerService(CoffeeNetNavigationDataExtractor.CoffeeNetServices.USER_SERVICE,
            coffeeNetCurrentUserServiceMock);

        List<GrantedAuthority> authorities = singletonList(new SimpleGrantedAuthority("ROLE_COFFEENET-ADMIN"));
        Optional<CoffeeNetUserDetails> user = of(new HumanCoffeeNetUser("username", "email", authorities));
        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(user);

        Optional<Map<String, List<CoffeeNetApp>>> extractedApps = sut.extractApps();
        Map<String, List<CoffeeNetApp>> coffeeNetApps = extractedApps.get();

        assertThat(coffeeNetApps).hasSize(2);
        assertThat(coffeeNetApps.get("apps")).hasSize(1);
        assertThat(coffeeNetApps.get("apps").get(0)).isSameAs(coffeeNetApp);
        assertThat(coffeeNetApps.get("profile")).hasSize(1);
        assertThat(coffeeNetApps.get("profile").get(0)).isSameAs(profileApp);
    }


    @Test
    public void extractAppsNoProfile() {

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        apps.put("apps", singletonList(coffeeNetApp));

        sut.registerService(CoffeeNetNavigationDataExtractor.CoffeeNetServices.APP_SERVICE, coffeeNetAppServiceMock);
        when(coffeeNetAppServiceMock.getApps(any())).thenReturn(apps);

        // user
        sut.registerService(CoffeeNetNavigationDataExtractor.CoffeeNetServices.USER_SERVICE,
            coffeeNetCurrentUserServiceMock);

        List<GrantedAuthority> authorities = singletonList(new SimpleGrantedAuthority("ROLE_COFFEENET-ADMIN"));
        Optional<CoffeeNetUserDetails> user = of(new HumanCoffeeNetUser("username", "email", authorities));
        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(user);

        Optional<Map<String, List<CoffeeNetApp>>> extractedApps = sut.extractApps();
        Map<String, List<CoffeeNetApp>> coffeeNetApps = extractedApps.get();

        assertThat(coffeeNetApps).hasSize(1);
        assertThat(coffeeNetApps.get("apps")).hasSize(1);
        assertThat(coffeeNetApps.get("apps").get(0)).isSameAs(coffeeNetApp);
        assertThat(coffeeNetApps.get("profile")).isNull();
    }


    @Test
    public void extractAppsNoUser() {

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        CoffeeNetApp coffeeNetApp2 = new CoffeeNetApp("Coffee App2", "coffeeapp.coffeenet",
                new HashSet<>(singletonList("ROLE_COFFEENET-USER")));
        apps.put("cna1", singletonList(coffeeNetApp));
        apps.put("cna2", singletonList(coffeeNetApp2));

        CoffeeNetApp profileApp = new CoffeeNetApp("Profile", "profile.coffeenet", emptySet());
        apps.put("profile", singletonList(profileApp));

        sut.registerService(CoffeeNetNavigationDataExtractor.CoffeeNetServices.APP_SERVICE, coffeeNetAppServiceMock);
        when(coffeeNetAppServiceMock.getApps(any())).thenReturn(apps);

        // user
        sut.registerService(CoffeeNetNavigationDataExtractor.CoffeeNetServices.USER_SERVICE,
            coffeeNetCurrentUserServiceMock);
        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(Optional.empty());

        Optional<Map<String, List<CoffeeNetApp>>> extractedApps = sut.extractApps();
        Map<String, List<CoffeeNetApp>> coffeeNetApps = extractedApps.get();

        assertThat(coffeeNetApps).hasSize(2);
        assertThat(coffeeNetApps.get("apps")).hasSize(2);
        assertThat(coffeeNetApps.get("apps").get(0)).isSameAs(coffeeNetApp);
        assertThat(coffeeNetApps.get("apps").get(1)).isSameAs(coffeeNetApp2);
        assertThat(coffeeNetApps.get("profile")).hasSize(1);
        assertThat(coffeeNetApps.get("profile").get(0)).isSameAs(profileApp);
    }


    @Test
    public void extractNoAppsService() {

        Optional<Map<String, List<CoffeeNetApp>>> extractedApps = sut.extractApps();
        assertThat(extractedApps).isSameAs(Optional.empty());
    }


    @Test
    public void extractNoUserService() {

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("Coffee App", "coffeeapp.coffeenet", emptySet());
        apps.put("cna1", singletonList(coffeeNetApp));

        CoffeeNetApp profileApp = new CoffeeNetApp("Profile", "profile.coffeenet", emptySet());
        apps.put("profile", singletonList(profileApp));

        sut.registerService(CoffeeNetNavigationDataExtractor.CoffeeNetServices.APP_SERVICE, coffeeNetAppServiceMock);
        when(coffeeNetAppServiceMock.getApps(any())).thenReturn(apps);

        Optional<Map<String, List<CoffeeNetApp>>> extractedApps = sut.extractApps();
        Map<String, List<CoffeeNetApp>> coffeeNetApps = extractedApps.get();

        assertThat(coffeeNetApps).hasSize(2);
        assertThat(coffeeNetApps.get("apps")).hasSize(1);
        assertThat(coffeeNetApps.get("apps").get(0)).isSameAs(coffeeNetApp);
        assertThat(coffeeNetApps.get("profile")).hasSize(1);
        assertThat(coffeeNetApps.get("profile").get(0)).isSameAs(profileApp);
    }


    @Test
    public void extractLogoutPath() {

        String logoutPath = sut.extractLogoutPath();
        assertThat(logoutPath).isSameAs("/logout");
    }


    @Test
    public void extractUser() {

        sut.registerService(CoffeeNetNavigationDataExtractor.CoffeeNetServices.USER_SERVICE,
            coffeeNetCurrentUserServiceMock);

        Optional<CoffeeNetUserDetails> user = of(new HumanCoffeeNetUser("username", "email", emptyList()));
        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(user);

        Optional<CurrentCoffeeNetUser> coffeeNetWebUser = sut.extractUser();
        assertThat(coffeeNetWebUser.get().getEmail()).isSameAs("email");
        assertThat(coffeeNetWebUser.get().getUsername()).isSameAs("username");
    }


    @Test
    public void extractUserNoUser() {

        sut.registerService(CoffeeNetNavigationDataExtractor.CoffeeNetServices.USER_SERVICE,
            coffeeNetCurrentUserServiceMock);

        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(Optional.empty());

        Optional<CurrentCoffeeNetUser> coffeeNetWebUser = sut.extractUser();
        assertThat(coffeeNetWebUser).isSameAs(Optional.empty());
    }


    @Test
    public void extractUserNoUserService() {

        Optional<CurrentCoffeeNetUser> coffeeNetWebUser = sut.extractUser();
        assertThat(coffeeNetWebUser).isSameAs(Optional.empty());
    }


    @Test
    public void orderingIsCaseInsensitive() {

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();

        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("x App a", "", emptySet());
        apps.put("cna1", singletonList(coffeeNetApp));

        CoffeeNetApp coffeeNetApp2 = new CoffeeNetApp("X App b", "", emptySet());
        apps.put("cna2", singletonList(coffeeNetApp2));

        CoffeeNetApp coffeeNetApp3 = new CoffeeNetApp("app a", "", emptySet());
        apps.put("cna3", singletonList(coffeeNetApp3));

        CoffeeNetApp coffeeNetApp4 = new CoffeeNetApp("App b", "", emptySet());
        apps.put("cna4", singletonList(coffeeNetApp4));

        sut.registerService(CoffeeNetNavigationDataExtractor.CoffeeNetServices.APP_SERVICE, coffeeNetAppServiceMock);
        when(coffeeNetAppServiceMock.getApps(any())).thenReturn(apps);

        // user
        sut.registerService(CoffeeNetNavigationDataExtractor.CoffeeNetServices.USER_SERVICE,
            coffeeNetCurrentUserServiceMock);
        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(Optional.empty());

        Optional<Map<String, List<CoffeeNetApp>>> extractedApps = sut.extractApps();
        Map<String, List<CoffeeNetApp>> coffeeNetApps = extractedApps.get();

        assertThat(coffeeNetApps).hasSize(1);
        assertThat(coffeeNetApps.get("apps")).hasSize(4);
        assertThat(coffeeNetApps.get("apps").get(0)).isSameAs(coffeeNetApp3);
        assertThat(coffeeNetApps.get("apps").get(1)).isSameAs(coffeeNetApp4);
        assertThat(coffeeNetApps.get("apps").get(2)).isSameAs(coffeeNetApp);
        assertThat(coffeeNetApps.get("apps").get(3)).isSameAs(coffeeNetApp2);
    }
}
