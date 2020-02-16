package rocks.coffeenet.legacy.autoconfigure.discovery.service;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.core.Is.is;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetAppTest {

    @Test
    public void isAllowedToAccess() {

        CoffeeNetApp coffeeNetApp = getAppWithRoles(asList("ROLE_ADMIN", "ROLE_USER"));

        boolean allowedToAccess = coffeeNetApp.isAllowedToAccessBy(new HashSet<>(singletonList("ROLE_USER")));
        assertThat(allowedToAccess, is(true));
    }


    @Test
    public void isAllowedToAccessNullAsAllowedAuthorities() {

        CoffeeNetApp coffeeNetApp = new CoffeeNetApp("AppName", "AppUrl", null);

        boolean allowedToAccess = coffeeNetApp.isAllowedToAccessBy(new HashSet<>(singletonList("ROLE_USER")));
        assertThat(allowedToAccess, is(true));
    }


    @Test
    public void isAllowedToAccessNoAppRolesDefined() {

        CoffeeNetApp coffeeNetApp = getAppWithRoles(null);

        boolean allowedToAccess = coffeeNetApp.isAllowedToAccessBy(new HashSet<>(singletonList("ROLE_ADMIN")));
        assertThat(allowedToAccess, is(true));
    }


    @Test
    public void isAllowedToAccessNoAppAndUserRolesDefined() {

        CoffeeNetApp coffeeNetApp = getAppWithRoles(null);

        boolean allowedToAccess = coffeeNetApp.isAllowedToAccessBy(new HashSet<>(singletonList(null)));
        assertThat(allowedToAccess, is(true));
    }


    @Test
    public void isAllowedToAccessCaseInsensitiveAppRole() {

        CoffeeNetApp coffeeNetApp = getAppWithRoles(singletonList("ROLE_admin"));

        boolean allowedToAccess = coffeeNetApp.isAllowedToAccessBy(new HashSet<>(singletonList("ROLE_ADMIN")));
        assertThat(allowedToAccess, is(true));
    }


    @Test
    public void isAllowedToAccessCaseInsensitiveUserRole() {

        CoffeeNetApp coffeeNetApp = getAppWithRoles(singletonList("ROLE_ADMIN"));

        boolean allowedToAccess = coffeeNetApp.isAllowedToAccessBy(new HashSet<>(singletonList("ROLE_admin")));
        assertThat(allowedToAccess, is(true));
    }


    @Test
    public void isNOTAllowedToAccess() {

        CoffeeNetApp coffeeNetApp = getAppWithRoles(asList("ROLE_ADMIN", "ROLE_SUPER_ADMIN"));

        boolean allowedToAccess = coffeeNetApp.isAllowedToAccessBy(new HashSet<>(
                    asList("ROLE_USER", "ROLE_EMPLOYEE")));
        assertThat(allowedToAccess, is(false));
    }


    @Test
    public void isNOTAllowedToAccessWithNoUserRolesDefined() {

        CoffeeNetApp coffeeNetApp = getAppWithRoles(singletonList("ROLE_ADMIN"));

        boolean allowedToAccess = coffeeNetApp.isAllowedToAccessBy(new HashSet<>(singletonList(null)));
        assertThat(allowedToAccess, is(false));
    }


    private CoffeeNetApp getAppWithRoles(List<String> roles) {

        Set<String> allowedAuthorities;

        if (roles == null) {
            allowedAuthorities = emptySet();
        } else {
            allowedAuthorities = new HashSet<>(roles);
        }

        return new CoffeeNetApp("AppName", "AppUrl", allowedAuthorities);
    }
}
