package coffee.synyx.autoconfigure.security.config;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetAuthoritiesExtractorTest {

    private CoffeeNetAuthoritiesExtractor sut;

    private Map<String, Object> userInfoHashMap;
    private List<String> authoritiesList;
    private Map<String, Object> testPrincipal;

    @Before
    public void setUp() {

        authoritiesList = new ArrayList<>();
        authoritiesList.add("ROLE_EMPLOYEE");
        authoritiesList.add("ROLE_COFFEENET-ADMIN");

        testPrincipal = new HashMap<>();
        testPrincipal.put("mail", "test@synyx.de");
        testPrincipal.put("username", "testPrincipal");
        testPrincipal.put("authorities", authoritiesList);

        userInfoHashMap = new HashMap<>();
        userInfoHashMap.put("clientOnly", false);
        userInfoHashMap.put("id", "testPrincipal");
        userInfoHashMap.put("name", "testPrincipal");
        userInfoHashMap.put("principal", testPrincipal);

        sut = new CoffeeNetAuthoritiesExtractor();
    }


    @Test
    public void extractAuthorities() {

        List<GrantedAuthority> grantedAuthorities = sut.extractAuthorities(userInfoHashMap);

        assertThat(grantedAuthorities.size(), is(2));
        assertThat(grantedAuthorities.contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE")), is(true));
        assertThat(grantedAuthorities.contains(new SimpleGrantedAuthority("ROLE_COFFEENET-ADMIN")), is(true));
    }


    @Test
    public void extractAuthoritiesReturnsDefaultAuthorityIfMapDoesNotContainPrincipalKey() {

        Map<String, Object> wrongUserInfoHashMap = new HashMap<>();
        wrongUserInfoHashMap.put("authorities", authoritiesList);

        List<GrantedAuthority> grantedAuthorities = sut.extractAuthorities(wrongUserInfoHashMap);

        assertThat(grantedAuthorities.size(), is(1));
        assertThat(grantedAuthorities.contains(new SimpleGrantedAuthority("ROLE_UNKNOWN")), is(true));
    }


    @Test
    public void extractAuthoritiesReturnsDefaultAuthorityIfPrincipalMapIsNotAMap() {

        userInfoHashMap.put("principal", "noHashMap");

        List<GrantedAuthority> grantedAuthorities = sut.extractAuthorities(userInfoHashMap);

        assertThat(grantedAuthorities.size(), is(1));
        assertThat(grantedAuthorities.contains(new SimpleGrantedAuthority("ROLE_UNKNOWN")), is(true));
    }


    @Test
    public void extractAuthoritiesReturnsDefaultAuthorityIfPrincipalMapDoesNotContainAuthoritiesKey() {

        testPrincipal.remove("authorities");

        List<GrantedAuthority> grantedAuthorities = sut.extractAuthorities(userInfoHashMap);

        assertThat(grantedAuthorities.size(), is(1));
        assertThat(grantedAuthorities.contains(new SimpleGrantedAuthority("ROLE_UNKNOWN")), is(true));
    }


    @Test
    public void extractAuthoritiesReturnsDefaultAuthorityIfAuthoritiesIsNotOfTypeCollection() {

        testPrincipal.put("authorities", "ROLE_EMPLOYEE");

        List<GrantedAuthority> grantedAuthorities = sut.extractAuthorities(userInfoHashMap);

        assertThat(grantedAuthorities.size(), is(1));
        assertThat(grantedAuthorities.contains(new SimpleGrantedAuthority("ROLE_UNKNOWN")), is(true));
    }
}
