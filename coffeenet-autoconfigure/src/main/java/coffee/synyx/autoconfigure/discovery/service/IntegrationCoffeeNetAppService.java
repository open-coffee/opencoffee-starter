package coffee.synyx.autoconfigure.discovery.service;

import com.netflix.appinfo.InstanceInfo;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.springframework.util.StringUtils.commaDelimitedListToSet;

import static java.util.stream.Collectors.toList;


/**
 * This implementation provides all registered CoffeeNet applications by the eureka service discovery.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class IntegrationCoffeeNetAppService implements CoffeeNetAppService {

    private DiscoveryClient discoveryClient;

    public IntegrationCoffeeNetAppService(DiscoveryClient discoveryClient) {

        this.discoveryClient = discoveryClient;
    }

    @Override
    public List<CoffeeNetApp> getApps() {

        return discoveryClient.getServices()
            .stream()
            .map(this::getAppInstance)
            .filter(Objects::nonNull)
            .collect(toList());
    }


    private CoffeeNetApp getAppInstance(String applicationName) {

        EurekaServiceInstance eurekaServiceInstance = discoveryClient.getInstances(applicationName)
            .stream()
            .filter(serviceInstance -> serviceInstance instanceof EurekaServiceInstance)
            .map(serviceInstance -> (EurekaServiceInstance) serviceInstance)
            .findFirst()
            .orElse(null);

        return toApp(eurekaServiceInstance);
    }


    private static CoffeeNetApp toApp(EurekaServiceInstance serviceInstance) {

        if (serviceInstance == null) {
            return null;
        }

        InstanceInfo instanceInfo = serviceInstance.getInstanceInfo();
        Set<String> allowedAuthorities = commaDelimitedListToSet(instanceInfo.getMetadata().get("allowedAuthorities"));

        return new CoffeeNetApp(instanceInfo.getVIPAddress(), instanceInfo.getHomePageUrl(), allowedAuthorities);
    }
}
