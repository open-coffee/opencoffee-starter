package coffee.synyx.autoconfigure.web.web;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.security.service.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.service.CoffeeNetUserDetails;

import java.util.Collection;

import static java.util.stream.Collectors.toList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
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

        Collection<CoffeeNetApp> coffeeNetApps = coffeeNetAppService.getApps();
        coffeeNetApps = filterAppsByRoles(coffeeNetApps, coffeeNetUserDetails);

        CoffeeNetApp profileApp = extractProfileApp(coffeeNetApps);
        coffeeNetApps.remove(profileApp);

        String logoutPath = coffeeNetWebProperties.getLogoutPath();

        CoffeeNetWebUser coffeeNetWebUser = new CoffeeNetWebUser(coffeeNetUserDetails.getUsername(),
                coffeeNetUserDetails.getEmail());

        return new CoffeeNetWeb(coffeeNetWebUser, coffeeNetApps, profileApp, logoutPath);
    }


    private static Collection<CoffeeNetApp> filterAppsByRoles(Collection<CoffeeNetApp> coffeeNetApps,
        CoffeeNetUserDetails coffeeNetUserDetails) {

        return coffeeNetApps.stream().filter(app ->
                    app.isAllowedToAccessBy(coffeeNetUserDetails.getAuthoritiesAsString())).collect(toList());
    }


    private CoffeeNetApp extractProfileApp(Collection<CoffeeNetApp> coffeeNetApps) {

        return coffeeNetApps.stream().filter(coffeeNetApp ->
                        coffeeNetApp.getName()
                        .equalsIgnoreCase(coffeeNetWebProperties.getProfileServiceName())).findFirst().orElse(null);
    }
}
