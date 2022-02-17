package rocks.coffeenet.legacy.autoconfigure.discovery.service;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.core.Is.is;


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

        Map<String, List<CoffeeNetApp>> coffeeNetApps = sut.getApps();
        assertThat(coffeeNetApps.entrySet(), hasSize(3));
        assertThat(coffeeNetApps.get("Coffee App").get(0).getName(), is("Coffee App"));
        assertThat(coffeeNetApps.get("Coffee App").get(0).getAuthorities(), is(emptyIterable()));
        assertThat(coffeeNetApps.get("Profile").get(0).getName(), is("Profile"));
        assertThat(coffeeNetApps.get("Profile").get(0).getAuthorities(), is(emptyIterable()));
        assertThat(coffeeNetApps.get("Coffee Admin App").get(0).getName(), is("Coffee Admin App"));
        assertThat(coffeeNetApps.get("Coffee Admin App").get(0).getAuthorities(), hasItem("ROLE_COFFEENET-ADMIN"));
    }


    @Test
    public void getAppsWithQuery() {

        Map<String, List<CoffeeNetApp>> coffeeNetApps = sut.getApps(AppQuery.builder().build());
        assertThat(coffeeNetApps.entrySet(), hasSize(3));
        assertThat(coffeeNetApps.get("Coffee App").get(0).getName(), is("Coffee App"));
        assertThat(coffeeNetApps.get("Coffee App").get(0).getAuthorities(), is(emptyIterable()));
        assertThat(coffeeNetApps.get("Profile").get(0).getName(), is("Profile"));
        assertThat(coffeeNetApps.get("Profile").get(0).getAuthorities(), is(emptyIterable()));
        assertThat(coffeeNetApps.get("Coffee Admin App").get(0).getName(), is("Coffee Admin App"));
        assertThat(coffeeNetApps.get("Coffee Admin App").get(0).getAuthorities(), hasItem("ROLE_COFFEENET-ADMIN"));
    }
}
