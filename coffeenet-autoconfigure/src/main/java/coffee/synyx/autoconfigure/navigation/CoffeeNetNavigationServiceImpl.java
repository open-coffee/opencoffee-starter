package coffee.synyx.autoconfigure.navigation;

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
public class CoffeeNetNavigationServiceImpl implements CoffeeNetNavigationService {

    private final CoffeeNetNavigationDataExtractor coffeeNetNavigationDataExtractor;

    CoffeeNetNavigationServiceImpl(CoffeeNetNavigationDataExtractor coffeeNetNavigationDataExtractor) {

        this.coffeeNetNavigationDataExtractor = coffeeNetNavigationDataExtractor;
    }

    @Override
    public CoffeeNetNavigationInformation get() {


        CurrentCoffeeNetUser currentCoffeeNetUser = coffeeNetNavigationDataExtractor.extractUser().orElse(null);
        Map<String, List<CoffeeNetApp>> apps = coffeeNetNavigationDataExtractor.extractApps()
            .orElseGet(Collections::emptyMap);
        String logoutPath = coffeeNetNavigationDataExtractor.extractLogoutPath();

        List<CoffeeNetApp> profileApps = apps.get("profile");
        CoffeeNetApp profileApp = null;

        if (profileApps != null && !profileApps.isEmpty()) {
            profileApp = profileApps.get(0);
        }

        List<CoffeeNetApp> coffeeNetApps = apps.get("apps");

        return new CoffeeNetNavigationInformation(currentCoffeeNetUser, coffeeNetApps, profileApp, logoutPath);
    }
}
