package coffee.synyx.autoconfigure.web;

import coffee.synyx.autoconfigure.discovery.service.AppQuery;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;


/**
 * This service exposes all information that are needed for the coffeenet navigation bar based in the server and client
 * side rendering engines like thymeleaf or javascript.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
public class CoffeeNetWebServiceWithDiscovery implements CoffeeNetWebService {

    private final CoffeeNetAppService coffeeNetAppService;
    private final CoffeeNetWebProperties coffeeNetWebProperties;

    CoffeeNetWebServiceWithDiscovery(CoffeeNetAppService coffeeNetAppService,
        CoffeeNetWebProperties coffeeNetWebProperties) {

        this.coffeeNetAppService = coffeeNetAppService;
        this.coffeeNetWebProperties = coffeeNetWebProperties;
    }

    @Override
    public CoffeeNetWeb get() {

        // apps
        String profileServiceName = coffeeNetWebProperties.getProfileServiceName();
        AppQuery query = AppQuery.builder().build();
        Map<String, List<CoffeeNetApp>> coffeeNetApps = coffeeNetAppService.getApps(query);

        List<CoffeeNetApp> profileApps = coffeeNetApps.get(profileServiceName);
        CoffeeNetApp profileApp = null;

        if (profileApps != null) {
            profileApp = profileApps.get(0);
            coffeeNetApps.remove(profileServiceName);
        }

        List<CoffeeNetApp> firstCoffeeNetApps = coffeeNetApps.entrySet()
            .stream()
            .map(entry -> entry.getValue().get(0))
            .sorted(Comparator.comparing(CoffeeNetApp::getName))
            .collect(toList());

        // logout path
        String logoutPath = coffeeNetWebProperties.getLogoutPath();

        return new CoffeeNetWeb(null, firstCoffeeNetApps, profileApp, logoutPath);
    }
}
