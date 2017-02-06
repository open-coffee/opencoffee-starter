package coffee.synyx.autoconfigure.web;

import coffee.synyx.autoconfigure.discovery.service.AppQuery;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.security.service.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.service.CoffeeNetUserDetails;

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
public class CoffeeNetWebServiceImpl implements CoffeeNetWebService {

    private final CoffeeNetCurrentUserService coffeeNetCurrentUserService;
    private final CoffeeNetAppService coffeeNetAppService;
    private final CoffeeNetWebProperties coffeeNetWebProperties;

    CoffeeNetWebServiceImpl(CoffeeNetCurrentUserService coffeeNetCurrentUserService,
        CoffeeNetAppService coffeeNetAppService, CoffeeNetWebProperties coffeeNetWebProperties) {

        this.coffeeNetCurrentUserService = coffeeNetCurrentUserService;
        this.coffeeNetAppService = coffeeNetAppService;
        this.coffeeNetWebProperties = coffeeNetWebProperties;
    }

    @Override
    public CoffeeNetWeb get() {

        CoffeeNetUserDetails coffeeNetUserDetails = coffeeNetCurrentUserService.get();

        // apps
        String profileServiceName = coffeeNetWebProperties.getProfileServiceName();
        AppQuery.Builder builder = AppQuery.builder();

        if (coffeeNetUserDetails != null) {
            builder.withRoles(coffeeNetUserDetails.getAuthoritiesAsString());
        }

        AppQuery query = builder.build();

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

        // user
        CoffeeNetWebUser coffeeNetWebUser = null;

        if (coffeeNetUserDetails != null) {
            coffeeNetWebUser = new CoffeeNetWebUser(coffeeNetUserDetails.getUsername(),
                    coffeeNetUserDetails.getEmail());
        }

        return new CoffeeNetWeb(coffeeNetWebUser, firstCoffeeNetApps, profileApp, logoutPath);
    }
}
