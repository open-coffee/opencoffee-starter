package rocks.coffeenet.legacy.autoconfigure.security.config;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;

import org.springframework.security.core.authority.AuthorityUtils;

import rocks.coffeenet.legacy.autoconfigure.security.service.HumanCoffeeNetUser;
import rocks.coffeenet.legacy.autoconfigure.security.service.MachineCoffeeNetUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.when;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetPrincipalExtractorTest {

    private CoffeeNetPrincipalExtractor sut;

    @Mock
    private AuthoritiesExtractor authoritiesExtractorMock;

    private Map<String, Object> userInfoHashMap;
    private Map<String, Object> testPrincipal;

    @Before
    public void setUp() {

        sut = new CoffeeNetPrincipalExtractor(authoritiesExtractorMock);

        List<String> authoritiesList = new ArrayList<>();
        authoritiesList.add("ROLE_EMPLOYEE");
        authoritiesList.add("ROLE_COFFEENET-ADMIN");

        testPrincipal = new HashMap<>();
        testPrincipal.put("mail", "coffy@coffeenet");
        testPrincipal.put("username", "coffy");
        testPrincipal.put("authorities", authoritiesList);

        userInfoHashMap = new HashMap<>();
        userInfoHashMap.put("clientOnly", false);
        userInfoHashMap.put("id", "coffy");
        userInfoHashMap.put("name", "coffy");
        userInfoHashMap.put("principal", testPrincipal);

        when(authoritiesExtractorMock.extractAuthorities(userInfoHashMap)).thenReturn(AuthorityUtils
                .createAuthorityList("ROLE_COFFEENET-USER"));
    }


    @Test
    public void extractPrincipalReturnMachineCoffeeUserIfClientOnlyIsTrue() {

        userInfoHashMap.put("clientOnly", true);

        Object principal = sut.extractPrincipal(userInfoHashMap);

        assertThat(principal, instanceOf(MachineCoffeeNetUser.class));

        MachineCoffeeNetUser machineCoffeeUser = (MachineCoffeeNetUser) principal;
        assertThat(machineCoffeeUser.getUsername(), is("coffy"));
        assertThat(machineCoffeeUser.getAuthorities().size(), is(1));
    }


    @Test
    public void extractPrincipalReturnsUnknownMachineCoffeeUserIfNameKeyIsMissing() {

        userInfoHashMap.put("clientOnly", true);
        userInfoHashMap.remove("name");

        Object principal = sut.extractPrincipal(userInfoHashMap);

        assertThat(principal, instanceOf(MachineCoffeeNetUser.class));

        MachineCoffeeNetUser machineCoffeeUser = (MachineCoffeeNetUser) principal;
        assertThat(machineCoffeeUser.getUsername(), is("UNKNOWN"));
        assertThat(machineCoffeeUser.getAuthorities().size(), is(1));
    }


    @Test
    public void extractPrincipalReturnsUnknownMachineCoffeeUserIfNameKeyIsNotOfTypeString() {

        userInfoHashMap.put("clientOnly", true);
        userInfoHashMap.put("name", false);

        Object principal = sut.extractPrincipal(userInfoHashMap);

        assertThat(principal, instanceOf(MachineCoffeeNetUser.class));

        MachineCoffeeNetUser machineCoffeeUser = (MachineCoffeeNetUser) principal;
        assertThat(machineCoffeeUser.getUsername(), is("UNKNOWN"));
        assertThat(machineCoffeeUser.getAuthorities().size(), is(1));
    }


    @Test
    public void extractPrincipalReturnHumanCoffeeUserIfClientOnlyKeyIsMissing() {

        userInfoHashMap.remove("clientOnly");

        Object principal = sut.extractPrincipal(userInfoHashMap);

        assertThat(principal, instanceOf(HumanCoffeeNetUser.class));

        HumanCoffeeNetUser humanCoffeeUser = (HumanCoffeeNetUser) principal;
        assertThat(humanCoffeeUser.getEmail(), is("coffy@coffeenet"));
        assertThat(humanCoffeeUser.getUsername(), is("coffy"));
        assertThat(humanCoffeeUser.getAuthorities().size(), is(1));
    }


    @Test
    public void extractPrincipalReturnHumanCoffeeUser() {

        Object principal = sut.extractPrincipal(userInfoHashMap);

        assertThat(principal, instanceOf(HumanCoffeeNetUser.class));

        HumanCoffeeNetUser humanCoffeeUser = (HumanCoffeeNetUser) principal;
        assertThat(humanCoffeeUser.getEmail(), is("coffy@coffeenet"));
        assertThat(humanCoffeeUser.getUsername(), is("coffy"));
        assertThat(humanCoffeeUser.getAuthorities().size(), is(1));
    }


    @Test
    public void extractPrincipalReturnsUnknownHumanCoffeeUserIfMapDoesNotContainPrincipalKey() {

        userInfoHashMap.remove("principal");

        Object principal = sut.extractPrincipal(userInfoHashMap);

        assertThat(principal, instanceOf(HumanCoffeeNetUser.class));

        HumanCoffeeNetUser humanCoffeeUser = (HumanCoffeeNetUser) principal;
        assertThat(humanCoffeeUser.getEmail(), is("UNKNOWN"));
        assertThat(humanCoffeeUser.getUsername(), is("UNKNOWN"));
        assertThat(humanCoffeeUser.getAuthorities().size(), is(1));
    }


    @Test
    public void extractPrincipalReturnsUnknownHumanCoffeeUserIfPrincipalIsNotOfTypeMap() {

        userInfoHashMap.put("principal", "someThingElse");

        Object principal = sut.extractPrincipal(userInfoHashMap);

        assertThat(principal, instanceOf(HumanCoffeeNetUser.class));

        HumanCoffeeNetUser humanCoffeeUser = (HumanCoffeeNetUser) principal;
        assertThat(humanCoffeeUser.getEmail(), is("UNKNOWN"));
        assertThat(humanCoffeeUser.getUsername(), is("UNKNOWN"));
        assertThat(humanCoffeeUser.getAuthorities().size(), is(1));
    }


    @Test
    public void extractPrincipalReturnsHumanCoffeeUserWithUnknownNameIfPrincipalDoesNotContainUsernameKey() {

        testPrincipal.remove("username");

        Object principal = sut.extractPrincipal(userInfoHashMap);

        assertThat(principal, instanceOf(HumanCoffeeNetUser.class));

        HumanCoffeeNetUser humanCoffeeUser = (HumanCoffeeNetUser) principal;
        assertThat(humanCoffeeUser.getEmail(), is("coffy@coffeenet"));
        assertThat(humanCoffeeUser.getUsername(), is("UNKNOWN"));
        assertThat(humanCoffeeUser.getAuthorities().size(), is(1));
    }


    @Test
    public void extractPrincipalReturnsHumanCoffeeUserWithUnknownNameIfUsernameIsNotOfTypeString() {

        testPrincipal.put("username", true);

        Object principal = sut.extractPrincipal(userInfoHashMap);

        assertThat(principal, instanceOf(HumanCoffeeNetUser.class));

        HumanCoffeeNetUser humanCoffeeUser = (HumanCoffeeNetUser) principal;
        assertThat(humanCoffeeUser.getEmail(), is("coffy@coffeenet"));
        assertThat(humanCoffeeUser.getUsername(), is("UNKNOWN"));
        assertThat(humanCoffeeUser.getAuthorities().size(), is(1));
    }


    @Test
    public void extractPrincipalReturnsHumanCoffeeUserWithUnknownMailIfPrincipalDoesNotContainMailKey() {

        testPrincipal.remove("mail");

        Object principal = sut.extractPrincipal(userInfoHashMap);

        assertThat(principal, instanceOf(HumanCoffeeNetUser.class));

        HumanCoffeeNetUser humanCoffeeUser = (HumanCoffeeNetUser) principal;
        assertThat(humanCoffeeUser.getEmail(), is("UNKNOWN"));
        assertThat(humanCoffeeUser.getUsername(), is("coffy"));
        assertThat(humanCoffeeUser.getAuthorities().size(), is(1));
    }


    @Test
    public void extractPrincipalReturnsHumanCoffeeUserWithUnknownMailIfMailIsNotOfTypeString() {

        testPrincipal.put("mail", true);

        Object principal = sut.extractPrincipal(userInfoHashMap);

        assertThat(principal, instanceOf(HumanCoffeeNetUser.class));

        HumanCoffeeNetUser humanCoffeeUser = (HumanCoffeeNetUser) principal;
        assertThat(humanCoffeeUser.getEmail(), is("UNKNOWN"));
        assertThat(humanCoffeeUser.getUsername(), is("coffy"));
        assertThat(humanCoffeeUser.getAuthorities().size(), is(1));
    }
}
