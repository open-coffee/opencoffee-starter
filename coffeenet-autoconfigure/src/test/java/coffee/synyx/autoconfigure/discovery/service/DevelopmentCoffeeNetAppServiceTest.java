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
public class DevelopmentCoffeeNetAppServiceTest {

    private DevelopmentCoffeeNetAppService sut;

    @Before
    public void setUp() {

        sut = new DevelopmentCoffeeNetAppService();
    }


    @Test
    public void getApps() {

        List<CoffeeNetApp> coffeeNetApps = sut.getApps();
        assertThat(coffeeNetApps, hasSize(3));
        assertThat(coffeeNetApps.get(0).getAuthorities(), is(emptyIterable()));
        assertThat(coffeeNetApps.get(1).getName(), is("Blog"));
        assertThat(coffeeNetApps.get(1).getAuthorities(), is(emptyIterable()));
        assertThat(coffeeNetApps.get(2).getName(), is("Host Tagger"));
        assertThat(coffeeNetApps.get(2).getAuthorities(), contains("ROLE_ADMIN"));
    }
}
