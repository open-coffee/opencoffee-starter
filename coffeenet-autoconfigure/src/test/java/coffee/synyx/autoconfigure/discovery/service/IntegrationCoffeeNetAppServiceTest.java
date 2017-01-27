package coffee.synyx.autoconfigure.discovery.service;

import com.netflix.appinfo.InstanceInfo;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class IntegrationCoffeeNetAppServiceTest {

    private IntegrationCoffeeNetAppService eurekaAppService;

    @Mock
    private DiscoveryClient discoveryClientMock;

    @Before
    public void setUp() {

        eurekaAppService = new IntegrationCoffeeNetAppService(discoveryClientMock);
    }


    @Test
    public void getApps() {

        when(discoveryClientMock.getServices()).thenReturn(asList("Profile", "Backpage", "Auth"));

        defineEurekaInstance("Profile");
        defineEurekaInstance("Backpage");
        defineEurekaInstance("Auth");

        List<CoffeeNetApp> coffeeNetApps = eurekaAppService.getApps();
        assertThat(coffeeNetApps, hasSize(3));
        assertThat(coffeeNetApps.get(0).getName(), is("Auth"));
        assertThat(coffeeNetApps.get(1).getName(), is("Backpage"));
        assertThat(coffeeNetApps.get(2).getName(), is("Profile"));
    }


    @Test
    public void getAppsAndInstancesNotFound() {

        when(discoveryClientMock.getServices()).thenReturn(asList("Frontpage", "Backpage"));

        List<CoffeeNetApp> coffeeNetApps = eurekaAppService.getApps();
        assertThat(coffeeNetApps, hasSize(0));
    }


    private void defineEurekaInstance(String name) {

        InstanceInfo instanceInfo = InstanceInfo.Builder.newBuilder().setAppName(name).setVIPAddress(name).build();
        instanceInfo.getMetadata().put("allowedAuthorities", "ROLE_ADMIN");

        EurekaServiceInstance eurekaServiceInstance = mock(EurekaServiceInstance.class);
        when(eurekaServiceInstance.getInstanceInfo()).thenReturn(instanceInfo);
        when(discoveryClientMock.getInstances(name)).thenReturn(singletonList(eurekaServiceInstance));
    }
}
