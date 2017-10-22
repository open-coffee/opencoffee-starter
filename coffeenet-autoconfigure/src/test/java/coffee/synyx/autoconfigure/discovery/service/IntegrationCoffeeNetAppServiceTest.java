package coffee.synyx.autoconfigure.discovery.service;

import com.netflix.appinfo.InstanceInfo;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class IntegrationCoffeeNetAppServiceTest {

    private IntegrationCoffeeNetAppService coffeeNetAppService;

    @Mock
    private DiscoveryClient discoveryClientMock;

    @Before
    public void setUp() {

        coffeeNetAppService = new IntegrationCoffeeNetAppService(discoveryClientMock);
    }


    @Test
    public void getApps() {

        String profileName = "Profile";
        EurekaServiceInstance profile = getServiceInstance(profileName, "ROLE_ADMIN, ROLE_USER");
        EurekaServiceInstance profile2 = getServiceInstance(profileName, "ROLE_USER");
        when(discoveryClientMock.getInstances(profileName)).thenReturn(asList(profile, profile2));

        String backPageName = "Backpage";
        EurekaServiceInstance backPage = getServiceInstance(backPageName, null);
        when(discoveryClientMock.getInstances(backPageName)).thenReturn(singletonList(backPage));

        String authName = "Auth";
        EurekaServiceInstance auth = getServiceInstance(authName, "ROLE_USER");
        when(discoveryClientMock.getInstances(authName)).thenReturn(singletonList(auth));

        when(discoveryClientMock.getServices()).thenReturn(asList(profileName, backPageName, authName));

        Map<String, List<CoffeeNetApp>> coffeeNetApps = coffeeNetAppService.getApps();
        assertThat(coffeeNetApps.entrySet(), hasSize(3));
        assertThat(coffeeNetApps.get(profileName), hasSize(2));
        assertThat(coffeeNetApps.get(profileName).get(0).getName(), is(profileName));
        assertThat(coffeeNetApps.get(profileName).get(0).getAuthorities(), hasSize(2));
        assertThat(coffeeNetApps.get(profileName).get(0).getAuthorities(), hasItem("ROLE_USER"));
        assertThat(coffeeNetApps.get(profileName).get(0).getAuthorities(), hasItem("ROLE_ADMIN"));
        assertThat(coffeeNetApps.get(profileName).get(1).getName(), is(profileName));
        assertThat(coffeeNetApps.get(profileName).get(1).getAuthorities(), hasItem("ROLE_USER"));
        assertThat(coffeeNetApps.get(backPageName), hasSize(1));
        assertThat(coffeeNetApps.get(backPageName).get(0).getName(), is(backPageName));
        assertThat(coffeeNetApps.get(backPageName).get(0).getAuthorities(), is(empty()));
        assertThat(coffeeNetApps.get(authName), hasSize(1));
        assertThat(coffeeNetApps.get(authName).get(0).getName(), is(authName));
        assertThat(coffeeNetApps.get(authName).get(0).getAuthorities(), hasSize(1));
        assertThat(coffeeNetApps.get(authName).get(0).getAuthorities(), hasItem("ROLE_USER"));
    }


    @Test
    public void getAppsFilteredByAppNamesAndRoles() {

        String frontPageName = "Frontpage";
        EurekaServiceInstance frontPage = getServiceInstance(frontPageName, null);
        EurekaServiceInstance frontPageWithUser = getServiceInstance(frontPageName, "ROLE_USER");
        EurekaServiceInstance frontPageWithAdminAndUser = getServiceInstance(frontPageName, "ROLE_USER, ROLE_ADMIN");
        when(discoveryClientMock.getInstances(frontPageName)).thenReturn(asList(frontPageWithUser,
                frontPageWithAdminAndUser, frontPage));

        when(discoveryClientMock.getServices()).thenReturn(asList(frontPageName, "Backpage"));

        AppQuery query = AppQuery.builder().withAppName(frontPageName).withRole("ROLE_USER").build();
        Map<String, List<CoffeeNetApp>> coffeeNetApps = coffeeNetAppService.getApps(query);
        assertThat(coffeeNetApps.entrySet(), hasSize(1));
        assertThat(coffeeNetApps.get(frontPageName), hasSize(3));
        assertThat(coffeeNetApps.get(frontPageName).get(0).getName(), is(frontPageName));
        assertThat(coffeeNetApps.get(frontPageName).get(0).getAuthorities(), hasItem("ROLE_USER"));
        assertThat(coffeeNetApps.get(frontPageName).get(1).getName(), is(frontPageName));
        assertThat(coffeeNetApps.get(frontPageName).get(1).getAuthorities(), hasItem("ROLE_USER"));
        assertThat(coffeeNetApps.get(frontPageName).get(2).getName(), is(frontPageName));
        assertThat(coffeeNetApps.get(frontPageName).get(2).getAuthorities(), is(empty()));
    }


    @Test
    public void getAppsFilteredByRoles() {

        String frontPageName = "Frontpage";
        EurekaServiceInstance frontPage = getServiceInstance(frontPageName, null);
        EurekaServiceInstance frontPageWithUser = getServiceInstance(frontPageName, "ROLE_USER");
        EurekaServiceInstance frontPageWithAdminAndUser = getServiceInstance(frontPageName, "ROLE_USER, ROLE_ADMIN");
        when(discoveryClientMock.getInstances(frontPageName)).thenReturn(asList(frontPageWithUser,
                frontPageWithAdminAndUser, frontPage));

        String backPageName = "Backpage";
        EurekaServiceInstance backPage = getServiceInstance(backPageName, "ROLE_USER, ROLE_ADMIN");
        when(discoveryClientMock.getInstances(backPageName)).thenReturn(singletonList(backPage));

        when(discoveryClientMock.getServices()).thenReturn(asList(frontPageName, backPageName));

        AppQuery query = AppQuery.builder().withRole("ROLE_USER").build();
        Map<String, List<CoffeeNetApp>> coffeeNetApps = coffeeNetAppService.getApps(query);
        assertThat(coffeeNetApps.entrySet(), hasSize(2));
        assertThat(coffeeNetApps.get(backPageName), hasSize(1));
        assertThat(coffeeNetApps.get(backPageName).get(0).getName(), is(backPageName));
        assertThat(coffeeNetApps.get(backPageName).get(0).getAuthorities(), hasItem("ROLE_USER"));
        assertThat(coffeeNetApps.get(frontPageName), hasSize(3));
        assertThat(coffeeNetApps.get(frontPageName).get(0).getName(), is(frontPageName));
        assertThat(coffeeNetApps.get(frontPageName).get(0).getAuthorities(), hasItem("ROLE_USER"));
        assertThat(coffeeNetApps.get(frontPageName).get(1).getName(), is(frontPageName));
        assertThat(coffeeNetApps.get(frontPageName).get(1).getAuthorities(), hasItem("ROLE_USER"));
        assertThat(coffeeNetApps.get(frontPageName).get(2).getName(), is(frontPageName));
        assertThat(coffeeNetApps.get(frontPageName).get(2).getAuthorities(), is(empty()));
    }


    @Test
    public void getAppsFilteredByNames() {

        String frontPageName = "Frontpage";
        EurekaServiceInstance frontPage = getServiceInstance(frontPageName, null);
        EurekaServiceInstance frontPageWithUser = getServiceInstance(frontPageName, "ROLE_USER");
        EurekaServiceInstance frontPageWithAdminAndUser = getServiceInstance(frontPageName, "ROLE_USER, ROLE_ADMIN");
        when(discoveryClientMock.getInstances(frontPageName)).thenReturn(asList(frontPageWithUser,
                frontPageWithAdminAndUser, frontPage));

        when(discoveryClientMock.getServices()).thenReturn(asList(frontPageName, "Backpage"));

        AppQuery query = AppQuery.builder().withAppName("Frontpage").build();
        Map<String, List<CoffeeNetApp>> coffeeNetApps = coffeeNetAppService.getApps(query);
        assertThat(coffeeNetApps.entrySet(), hasSize(1));
        assertThat(coffeeNetApps.get(frontPageName), hasSize(3));
        assertThat(coffeeNetApps.get(frontPageName).get(0).getName(), is(frontPageName));
        assertThat(coffeeNetApps.get(frontPageName).get(0).getAuthorities(), hasItem("ROLE_USER"));
        assertThat(coffeeNetApps.get(frontPageName).get(1).getName(), is(frontPageName));
        assertThat(coffeeNetApps.get(frontPageName).get(1).getAuthorities(), hasItem("ROLE_USER"));
        assertThat(coffeeNetApps.get(frontPageName).get(2).getName(), is(frontPageName));
        assertThat(coffeeNetApps.get(frontPageName).get(2).getAuthorities(), is(empty()));
    }


    @Test
    public void getAppsFilteredByCaseInsensitiveNames() {

        String frontPageName = "Frontpage";
        String frontPageNameCI = "frontpage";
        EurekaServiceInstance frontPageWithAdminAndUser = getServiceInstance(frontPageName, null);
        when(discoveryClientMock.getInstances(frontPageName)).thenReturn(singletonList(frontPageWithAdminAndUser));

        when(discoveryClientMock.getServices()).thenReturn(singletonList(frontPageName));

        AppQuery query = AppQuery.builder().withAppName(frontPageNameCI).build();
        Map<String, List<CoffeeNetApp>> coffeeNetApps = coffeeNetAppService.getApps(query);
        assertThat(coffeeNetApps.entrySet(), hasSize(1));
        assertThat(coffeeNetApps.get(frontPageNameCI).get(0).getName(), is(frontPageName));
    }


    @Test
    public void getAppsNotOfTypeEurekaServiceInstance() {

        String frontPageName = "frontpage";
        ServiceInstance serviceInstance = mock(ServiceInstance.class);
        when(discoveryClientMock.getInstances(frontPageName)).thenReturn(singletonList(serviceInstance));
        when(discoveryClientMock.getServices()).thenReturn(singletonList(frontPageName));

        AppQuery query = AppQuery.builder().withAppName(frontPageName).build();
        Map<String, List<CoffeeNetApp>> coffeeNetApps = coffeeNetAppService.getApps(query);
        assertThat(coffeeNetApps.entrySet(), hasSize(0));
    }


    @Test
    public void getAppsAndInstancesNotFound() {

        when(discoveryClientMock.getServices()).thenReturn(emptyList());

        Map<String, List<CoffeeNetApp>> coffeeNetApps = coffeeNetAppService.getApps();
        assertThat(coffeeNetApps.entrySet(), hasSize(0));
    }


    private EurekaServiceInstance getServiceInstance(String name, String roles) {

        InstanceInfo instanceInfo = InstanceInfo.Builder.newBuilder().setAppName(name).setVIPAddress(name).build();

        if (roles != null) {
            instanceInfo.getMetadata().put("allowedAuthorities", roles);
        }

        EurekaServiceInstance eurekaServiceInstance = mock(EurekaServiceInstance.class);
        when(eurekaServiceInstance.getInstanceInfo()).thenReturn(instanceInfo);

        return eurekaServiceInstance;
    }
}
