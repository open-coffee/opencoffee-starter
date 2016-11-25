package coffee.synyx.autoconfigure.discovery.endpoint;

import coffee.synyx.autoconfigure.discovery.service.App;
import coffee.synyx.autoconfigure.discovery.service.AppService;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.core.Is.is;

import static org.mockito.Mockito.when;

import static java.util.Arrays.asList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetAppsEndpointTest {

    private CoffeeNetAppsEndpoint sut;

    @Mock
    private AppService appServiceMock;

    @Before
    public void setUp() {

        this.sut = new CoffeeNetAppsEndpoint(appServiceMock);
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

        when(appServiceMock.getApps()).thenReturn(asList(new App("name1", "url1"), new App("name2", "url2")));

        List<App> filteredAppList = sut.invoke();
        assertThat(filteredAppList, hasSize(2));
        assertThat(filteredAppList.get(0).getName(), is("name1"));
        assertThat(filteredAppList.get(1).getName(), is("name2"));
    }
}
