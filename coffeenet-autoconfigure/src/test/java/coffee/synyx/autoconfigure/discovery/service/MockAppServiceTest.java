package coffee.synyx.autoconfigure.discovery.service;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.hasSize;


/**
 * @author  Tobias Schneider
 */
public class MockAppServiceTest {

    private MockAppService sut;

    @Before
    public void setUp() {

        sut = new MockAppService();
    }


    @Test
    public void getApps() {

        List<App> apps = sut.getApps();
        assertThat(apps, hasSize(3));
        assertThat(apps.get(0).getName(), is("Homepage"));
        assertThat(apps.get(0).getAllowedAuthorities(), is(emptyIterable()));
        assertThat(apps.get(1).getName(), is("Blog"));
        assertThat(apps.get(1).getAllowedAuthorities(), is(emptyIterable()));
        assertThat(apps.get(2).getName(), is("Host Tagger"));
        assertThat(apps.get(2).getAllowedAuthorities(), contains("ROLE_ADMIN"));
    }
}
