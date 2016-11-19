package coffee.synyx.autoconfigure.discovery.service;

import coffee.synyx.autoconfigure.security.user.HumanCoffeeNetUser;

import org.junit.Test;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.core.Is.is;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class AppTest {

    @Test
    public void isAllowedToAccess() {

        App app = getAppWithRoles(asList("ROLE_ADMIN", "ROLE_USER"));
        HumanCoffeeNetUser humanCoffeeUser = getHumanCoffeeUserWithRole(singletonList("ROLE_USER"));

        boolean allowedToAccess = app.isAllowedToAccessBy(humanCoffeeUser);
        assertThat(allowedToAccess, is(true));
    }


    @Test
    public void isAllowedToAccessNullAsAllowedAuthorities() {

        App app = new App("AppName", "AppUrl", null);
        HumanCoffeeNetUser humanCoffeeUser = getHumanCoffeeUserWithRole(singletonList("ROLE_USER"));

        boolean allowedToAccess = app.isAllowedToAccessBy(humanCoffeeUser);
        assertThat(allowedToAccess, is(true));
    }


    @Test
    public void isAllowedToAccessNoAppRolesDefined() {

        App app = getAppWithRoles(null);
        HumanCoffeeNetUser humanCoffeeUser = getHumanCoffeeUserWithRole(singletonList("ROLE_ADMIN"));

        boolean allowedToAccess = app.isAllowedToAccessBy(humanCoffeeUser);
        assertThat(allowedToAccess, is(true));
    }


    @Test
    public void isAllowedToAccessNoAppAndUserRolesDefined() {

        App app = getAppWithRoles(null);
        HumanCoffeeNetUser humanCoffeeUser = getHumanCoffeeUserWithRole(null);

        boolean allowedToAccess = app.isAllowedToAccessBy(humanCoffeeUser);
        assertThat(allowedToAccess, is(true));
    }


    @Test
    public void isAllowedToAccessCaseInsensitiveAppRole() {

        App app = getAppWithRoles(singletonList("ROLE_admin"));
        HumanCoffeeNetUser humanCoffeeUser = getHumanCoffeeUserWithRole(singletonList("ROLE_ADMIN"));

        boolean allowedToAccess = app.isAllowedToAccessBy(humanCoffeeUser);
        assertThat(allowedToAccess, is(true));
    }


    @Test
    public void isAllowedToAccessCaseInsensitiveUserRole() {

        App app = getAppWithRoles(singletonList("ROLE_ADMIN"));
        HumanCoffeeNetUser humanCoffeeUser = getHumanCoffeeUserWithRole(singletonList("ROLE_admin"));

        boolean allowedToAccess = app.isAllowedToAccessBy(humanCoffeeUser);
        assertThat(allowedToAccess, is(true));
    }


    @Test
    public void isNOTAllowedToAccess() {

        App app = getAppWithRoles(asList("ROLE_ADMIN", "ROLE_SUPER_ADMIN"));
        HumanCoffeeNetUser humanCoffeeUser = getHumanCoffeeUserWithRole(asList("ROLE_USER", "ROLE_EMPLOYEE"));

        boolean allowedToAccess = app.isAllowedToAccessBy(humanCoffeeUser);
        assertThat(allowedToAccess, is(false));
    }


    @Test
    public void isNOTAllowedToAccessWithNoUserRolesDefined() {

        App app = getAppWithRoles(singletonList("ROLE_ADMIN"));
        HumanCoffeeNetUser humanCoffeeUser = getHumanCoffeeUserWithRole(null);

        boolean allowedToAccess = app.isAllowedToAccessBy(humanCoffeeUser);
        assertThat(allowedToAccess, is(false));
    }


    private HumanCoffeeNetUser getHumanCoffeeUserWithRole(List<String> roles) {

        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if (roles != null) {
            grantedAuthorities.addAll(roles.stream().map(SimpleGrantedAuthority::new).collect(toList()));
        }

        return new HumanCoffeeNetUser("username", "emailUsername", grantedAuthorities);
    }


    private App getAppWithRoles(List<String> roles) {

        Set<String> allowedAuthorities;

        if (roles == null) {
            allowedAuthorities = emptySet();
        } else {
            allowedAuthorities = new HashSet<>(roles);
        }

        return new App("AppName", "AppUrl", allowedAuthorities);
    }
}
