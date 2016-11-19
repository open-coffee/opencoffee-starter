package coffee.synyx.autoconfigure.discovery.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;
import org.springframework.cloud.netflix.eureka.InstanceInfoFactory;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

import static org.junit.Assert.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class EurekaAppServiceTest {

    private EurekaAppService eurekaAppService;

    @Mock
    private DiscoveryClient discoveryClientMock;

    @Before
    public void setUp() {

        eurekaAppService = new EurekaAppService(discoveryClientMock);
    }


    @Test
    public void getApps() {

        when(discoveryClientMock.getServices()).thenReturn(asList("Frontpage", "Backpage"));

        InstanceInfo instanceInfo = new InstanceInfoFactory().create(new MyDataCenterInstanceConfig());
        instanceInfo.getMetadata().put("allowedAuthorities", "ROLE_ADMIN");

        EurekaServiceInstance eurekaServiceInstance = mock(EurekaServiceInstance.class);
        when(eurekaServiceInstance.getInstanceInfo()).thenReturn(instanceInfo);
        when(discoveryClientMock.getInstances("Frontpage")).thenReturn(singletonList(eurekaServiceInstance));

        List<App> apps = eurekaAppService.getApps();
        assertThat(apps, hasSize(1));
        assertThat(apps.get(0).getAllowedAuthorities(), contains("ROLE_ADMIN"));
    }


    @Test
    public void getAppsAndInstancesNotFound() {

        when(discoveryClientMock.getServices()).thenReturn(asList("Frontpage", "Backpage"));

        List<App> apps = eurekaAppService.getApps();
        assertThat(apps, hasSize(0));
    }
}
