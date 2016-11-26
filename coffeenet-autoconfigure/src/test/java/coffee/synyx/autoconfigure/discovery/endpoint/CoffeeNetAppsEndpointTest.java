package coffee.synyx.autoconfigure.discovery.endpoint;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

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
    private CoffeeNetAppService coffeeNetAppServiceMock;

    @Before
    public void setUp() {

        this.sut = new CoffeeNetAppsEndpoint(coffeeNetAppServiceMock);
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

        when(coffeeNetAppServiceMock.getApps()).thenReturn(asList(
                new CoffeeNetApp("name1", "url1", new HashSet<>(singletonList("ROLE_ADMIN"))),
                new CoffeeNetApp("name2", "url2", new HashSet<>(singletonList("ROLE_USER")))));

        List<CoffeeNetApp> filteredCoffeeNetAppList = sut.invoke();
        assertThat(filteredCoffeeNetAppList, hasSize(2));
        assertThat(filteredCoffeeNetAppList.get(0).getName(), is("name1"));
        assertThat(filteredCoffeeNetAppList.get(1).getName(), is("name2"));
    }
}
