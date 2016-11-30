package coffee.synyx.autoconfigure.discovery.endpoint;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.core.Is.is;

import static org.mockito.Mockito.when;

import static java.util.Collections.emptySet;


/**
 * @author  Tobias Schneider
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetAppsEndpointNoFilterTest {

    @Mock
    private CoffeeNetAppService coffeeNetAppServiceMock;

    @Test
    public void testDefaultValues() {

        when(coffeeNetAppServiceMock.getApps()).thenReturn(Collections.singletonList(
                new CoffeeNetApp("name", "url", emptySet())));

        CoffeeNetAppsEndpointNoFilter sut = new CoffeeNetAppsEndpointNoFilter(coffeeNetAppServiceMock);

        assertThat(sut.getId(), is("coffeenet/apps"));
        assertThat(sut.isEnabled(), is(true));
        assertThat(sut.isSensitive(), is(false));
        assertThat(sut.invoke(), hasSize(1));
        assertThat(sut.invoke().get(0).getName(), is("name"));
    }
}
