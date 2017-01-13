package coffee.synyx.autoconfigure.web.web;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.security.service.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.service.HumanCoffeeNetUser;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetWebServiceImplTest {

    private CoffeeNetWebServiceImpl sut;

    @Mock
    private CoffeeNetCurrentUserService coffeeNetCurrentUserServiceMock;
    @Mock
    private CoffeeNetAppService coffeeNetAppServiceMock;

    @Before
    public void setUp() throws Exception {

        sut = new CoffeeNetWebServiceImpl(coffeeNetCurrentUserServiceMock, coffeeNetAppServiceMock,
                new CoffeeNetWebProperties());
    }


    @Test
    public void get() {

        CoffeeNetApp profileApp = new CoffeeNetApp("profile", "url", emptySet());
        CoffeeNetApp someApp = new CoffeeNetApp("some", "url", emptySet());

        List<CoffeeNetApp> apps = new ArrayList<>();
        apps.add(profileApp);
        apps.add(someApp);
        when(coffeeNetAppServiceMock.getApps()).thenReturn(apps);

        HumanCoffeeNetUser user = new HumanCoffeeNetUser("username", "email", emptyList());
        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(user);

        CoffeeNetWeb coffeeNetWeb = sut.get();
        assertThat(coffeeNetWeb.getLogoutPath()).isEqualTo("/logout");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getUsername()).isSameAs("username");
        assertThat(coffeeNetWeb.getCoffeeNetWebUser().getEmail()).isSameAs("email");
        assertThat(coffeeNetWeb.getProfileApp()).isSameAs(profileApp);
        assertThat(coffeeNetWeb.getCoffeeNetApps()).hasSize(1);
        assertThat(coffeeNetWeb.getCoffeeNetApps()).contains(someApp);
    }
}
