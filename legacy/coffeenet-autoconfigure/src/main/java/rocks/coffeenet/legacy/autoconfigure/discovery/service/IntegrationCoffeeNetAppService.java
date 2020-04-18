package rocks.coffeenet.legacy.autoconfigure.discovery.service;

import com.netflix.appinfo.InstanceInfo;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Supplier;

import static org.springframework.util.StringUtils.commaDelimitedListToSet;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;


/**
 * This implementation provides all registered CoffeeNet applications by the eureka service discovery.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class IntegrationCoffeeNetAppService implements CoffeeNetAppService {

    private final DiscoveryClient discoveryClient;

    public IntegrationCoffeeNetAppService(DiscoveryClient discoveryClient) {

        this.discoveryClient = discoveryClient;
    }

    @Override
    public Map<String, List<CoffeeNetApp>> getApps() {

        return getApps(AppQuery.builder().build());
    }


    @Override
    public Map<String, List<CoffeeNetApp>> getApps(AppQuery query) {

        Supplier<Map<String, List<CoffeeNetApp>>> map = () -> new TreeMap<>(CASE_INSENSITIVE_ORDER);

        return discoveryClient.getServices()
            .stream()
            .filter(appName -> query.getAppNames().isEmpty() || query.getAppNames().contains(appName))
            .map(appName -> getAppInstances(appName, query))
            .filter(coffeeNetApps -> !coffeeNetApps.isEmpty())
            .collect(toMap(coffeeNetApps -> coffeeNetApps.get(0).getName(), coffeeNetApps -> coffeeNetApps,
                    (v1, v2) -> v1,
                    map));
    }


    private List<CoffeeNetApp> getAppInstances(String appName, AppQuery query) {

        return discoveryClient.getInstances(appName)
            .stream()
            .map(IntegrationCoffeeNetAppService::toApp)
            .filter(Objects::nonNull)
            .filter(coffeeNetApp ->
                        query.getAppRoles().isEmpty() || coffeeNetApp.isAllowedToAccessBy(query.getAppRoles()))
            .collect(toList());
    }


    private static CoffeeNetApp toApp(ServiceInstance serviceInstance) {

        if (!(serviceInstance instanceof EurekaServiceInstance)) {
            return null;
        }

        EurekaServiceInstance eurekaServiceInstance = (EurekaServiceInstance) serviceInstance;
        InstanceInfo instanceInfo = eurekaServiceInstance.getInstanceInfo();
        Set<String> allowedAuthorities = commaDelimitedListToSet(instanceInfo.getMetadata().get("allowedAuthorities"))
                .stream().map(String::trim).collect(toSet());

        return new CoffeeNetApp(instanceInfo.getVIPAddress(), instanceInfo.getHomePageUrl(), allowedAuthorities);
    }
}
