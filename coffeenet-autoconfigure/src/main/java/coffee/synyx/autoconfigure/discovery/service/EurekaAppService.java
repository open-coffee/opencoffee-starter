package coffee.synyx.autoconfigure.discovery.service;

import com.netflix.appinfo.InstanceInfo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;

import java.util.List;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.INTEGRATION;

import static java.util.stream.Collectors.toList;


/**
 * This implementation provides all registered CoffeeNet applications by the eureka service discovery.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = INTEGRATION)
public class EurekaAppService implements AppService {

    private DiscoveryClient discoveryClient;

    @Autowired
    public EurekaAppService(DiscoveryClient discoveryClient) {

        this.discoveryClient = discoveryClient;
    }

    @Override
    public List<App> getApps() {

        return discoveryClient.getServices()
            .stream()
            .map(this::getAppInstance)
            .filter(app -> app != null)
            .collect(toList());
    }


    private App getAppInstance(String applicationName) {

        EurekaServiceInstance eurekaServiceInstance = discoveryClient.getInstances(applicationName)
                .stream()
                .filter(serviceInstance -> serviceInstance instanceof EurekaServiceInstance)
                .map(serviceInstance -> (EurekaServiceInstance) serviceInstance)
                .findFirst()
                .orElse(null);

        return toApp(eurekaServiceInstance);
    }


    private static App toApp(EurekaServiceInstance serviceInstance) {

        if (serviceInstance == null) {
            return null;
        }

        InstanceInfo instanceInfo = serviceInstance.getInstanceInfo();

        return new App(instanceInfo.getVIPAddress(), instanceInfo.getHomePageUrl());
    }
}
