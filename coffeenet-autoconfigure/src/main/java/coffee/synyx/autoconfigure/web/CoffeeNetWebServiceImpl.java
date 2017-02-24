package coffee.synyx.autoconfigure.web;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;

import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * This service exposes all information that are needed for the coffeenet navigation bar based in the server and client
 * side rendering engines like thymeleaf or javascript.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.17.0
 */
public class CoffeeNetWebServiceImpl implements CoffeeNetWebService {

    private final CoffeeNetWebExtractor coffeeNetWebExtractor;

    CoffeeNetWebServiceImpl(CoffeeNetWebExtractor coffeeNetWebExtractor) {

        this.coffeeNetWebExtractor = coffeeNetWebExtractor;
    }

    @Override
    public CoffeeNetWeb get() {

        CoffeeNetWebUser coffeeNetWebUser = coffeeNetWebExtractor.extractUser().orElse(null);
        Map<String, List<CoffeeNetApp>> apps = coffeeNetWebExtractor.extractApps().orElseGet(Collections::emptyMap);
        String logoutPath = coffeeNetWebExtractor.extractLogoutPath();

        List<CoffeeNetApp> profileApps = apps.get("profile");
        CoffeeNetApp profileApp = null;

        if (profileApps != null && !profileApps.isEmpty()) {
            profileApp = profileApps.get(0);
        }

        List<CoffeeNetApp> coffeeNetApps = apps.get("apps");

        return new CoffeeNetWeb(coffeeNetWebUser, coffeeNetApps, profileApp, logoutPath);
    }
}
