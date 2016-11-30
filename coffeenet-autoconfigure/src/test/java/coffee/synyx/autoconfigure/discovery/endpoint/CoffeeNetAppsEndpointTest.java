package coffee.synyx.autoconfigure.discovery.endpoint;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.user.CoffeeNetUserDetails;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.core.Is.is;

import static org.mockito.Mockito.when;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetAppsEndpointTest {

    @Mock
    private CoffeeNetAppService coffeeNetAppServiceMock;
    @Mock
    private CoffeeNetCurrentUserService coffeeNetCurrentUserServiceMock;

    @Test
    public void testDefaultValues() {

        CoffeeNetAppsEndpoint sut = new CoffeeNetAppsEndpoint(coffeeNetAppServiceMock, coffeeNetCurrentUserServiceMock);

        assertThat(sut.getId(), is("coffeenet/apps"));
        assertThat(sut.isEnabled(), is(true));
        assertThat(sut.isSensitive(), is(false));
    }


    @Test
    public void invokeWithEmptyUserRoles() {

        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(new UserStub(emptySet()));

        CoffeeNetAppsEndpoint sut = new CoffeeNetAppsEndpoint(coffeeNetAppServiceMock, coffeeNetCurrentUserServiceMock);

        when(coffeeNetAppServiceMock.getApps()).thenReturn(asList(
                new CoffeeNetApp("NoRights", "urlNoRights", emptySet()),
                new CoffeeNetApp("OneRight", "urlAdminRights", rolesToSet("ROLE_COFFEENET-ADMIN")),
                new CoffeeNetApp("MultipleRights", "urlUserRights",
                    rolesToSet("ROLE_COFFEENET-USER", "ROLE_COFFEENET-ADMIN"))));

        List<CoffeeNetApp> filteredCoffeeNetAppList = sut.invoke();
        assertThat(filteredCoffeeNetAppList, hasSize(1));
        assertThat(filteredCoffeeNetAppList.get(0).getName(), is("NoRights"));
    }


    @Test
    public void invokeWithOneUserRoles() {

        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(new UserStub(
                rolesToAuthorities("ROLE_COFFEENET-ADMIN")));

        CoffeeNetAppsEndpoint sut = new CoffeeNetAppsEndpoint(coffeeNetAppServiceMock, coffeeNetCurrentUserServiceMock);

        when(coffeeNetAppServiceMock.getApps()).thenReturn(asList(
                new CoffeeNetApp("NoRights", "urlNoRights", emptySet()),
                new CoffeeNetApp("OneRight", "urlAdminRights", rolesToSet("ROLE_COFFEENET-ADMIN")),
                new CoffeeNetApp("MultipleRights", "urlUserRights",
                    rolesToSet("ROLE_COFFEENET-USER", "ROLE_COFFEENET-ADMIN"))));

        List<CoffeeNetApp> filteredCoffeeNetAppList = sut.invoke();
        assertThat(filteredCoffeeNetAppList, hasSize(3));
        assertThat(filteredCoffeeNetAppList.get(0).getName(), is("NoRights"));
        assertThat(filteredCoffeeNetAppList.get(1).getName(), is("OneRight"));
        assertThat(filteredCoffeeNetAppList.get(2).getName(), is("MultipleRights"));
    }


    @Test
    public void invokeMultipleUserRoles() {

        when(coffeeNetCurrentUserServiceMock.get()).thenReturn(new UserStub(
                rolesToAuthorities("ROLE_COFFEENET-ADMIN", "ROLE_COFFEENET-SADMIN")));

        CoffeeNetAppsEndpoint sut = new CoffeeNetAppsEndpoint(coffeeNetAppServiceMock, coffeeNetCurrentUserServiceMock);

        when(coffeeNetAppServiceMock.getApps()).thenReturn(asList(
                new CoffeeNetApp("NoRights", "urlNoRights", emptySet()),
                new CoffeeNetApp("OneRight", "urlAdminRights", rolesToSet("ROLE_COFFEENET-ADMIN")),
                new CoffeeNetApp("MultipleRights", "urlUserRights",
                    rolesToSet("ROLE_COFFEENET-USER", "ROLE_COFFEENET-ADMIN")),
                new CoffeeNetApp("SAdminRight", "SAdminRight", rolesToSet("ROLE_COFFEENET-SADMIN"))));

        List<CoffeeNetApp> filteredCoffeeNetAppList = sut.invoke();
        assertThat(filteredCoffeeNetAppList, hasSize(4));
        assertThat(filteredCoffeeNetAppList.get(0).getName(), is("NoRights"));
        assertThat(filteredCoffeeNetAppList.get(1).getName(), is("OneRight"));
        assertThat(filteredCoffeeNetAppList.get(2).getName(), is("MultipleRights"));
        assertThat(filteredCoffeeNetAppList.get(3).getName(), is("SAdminRight"));
    }


    private Collection<? extends GrantedAuthority> rolesToAuthorities(String... roles) {

        return Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(toList());
    }


    private HashSet<String> rolesToSet(String... roles) {

        return new HashSet<>(asList(roles));
    }

    private class UserStub implements CoffeeNetUserDetails {

        private Collection<? extends GrantedAuthority> authorities;

        UserStub(Collection<? extends GrantedAuthority> authorities) {

            this.authorities = authorities;
        }

        @Override
        public String getUsername() {

            return null;
        }


        @Override
        public String getEmail() {

            return null;
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {

            return authorities;
        }


        @Override
        public boolean isAdmin() {

            return false;
        }


        @Override
        public boolean isMachineUser() {

            return false;
        }
    }
}
