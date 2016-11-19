package coffee.synyx.autoconfigure.discovery.endpoint;

import coffee.synyx.autoconfigure.discovery.service.App;
import coffee.synyx.autoconfigure.discovery.service.AppService;
import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.user.HumanCoffeeNetUser;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.core.Is.is;

import static org.mockito.Mockito.when;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetAppsEndpointTest {

    private CoffeeNetAppsEndpoint sut;

    @Mock
    private AppService appServiceMock;
    @Mock
    private CoffeeNetCurrentUserService coffeeNetCurrentUserServiceMock;

    @Before
    public void setUp() {

        this.sut = new CoffeeNetAppsEndpoint(appServiceMock, coffeeNetCurrentUserServiceMock);
    }


    @Test
    public void getID() {

        assertThat(sut.getId(), is("coffeenet/apps"));
    }


    @Test
    public void isEnabled() {

        assertThat(sut.isEnabled(), is(true));
    }


    @Test
    public void isSensitive() {

        assertThat(sut.isSensitive(), is(false));
    }


    @Test
    public void invoke() {

        when(appServiceMock.getApps()).thenReturn(asList(
                new App("name1", "url1", new HashSet<>(singletonList("ROLE_ADMIN"))),
                new App("name2", "url2", new HashSet<>(singletonList("ROLE_USER")))));

        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(getHumanCoffeeUserWithRole("ROLE_ADMIN"));

        List<App> filteredAppList = sut.invoke();
        assertThat(filteredAppList, hasSize(1));
        assertThat(filteredAppList.get(0).getName(), is("name1"));
    }


    private HumanCoffeeNetUser getHumanCoffeeUserWithRole(String role) {

        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role));

        return new HumanCoffeeNetUser("username", "emailUsername", grantedAuthorities);
    }
}
