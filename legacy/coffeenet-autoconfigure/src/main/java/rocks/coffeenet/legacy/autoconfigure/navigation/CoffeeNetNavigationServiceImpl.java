package rocks.coffeenet.legacy.autoconfigure.navigation;

import rocks.coffeenet.legacy.autoconfigure.discovery.service.CoffeeNetApp;

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

    private final CoffeeNetNavigationDataExtractor dataExtractor;

    CoffeeNetNavigationServiceImpl(CoffeeNetNavigationDataExtractor dataExtractor) {

        this.dataExtractor = dataExtractor;
    }

    @Override
    public CoffeeNetNavigationInformation get() {

        CurrentCoffeeNetUser currentCoffeeNetUser = dataExtractor.extractUser().orElse(null);
        CoffeeNetNavigationAppInformation appInformation = dataExtractor.extractAppInformation().orElse(null);
        Map<String, List<CoffeeNetApp>> apps = dataExtractor.extractApps().orElseGet(Collections::emptyMap);
        String logoutPath = dataExtractor.extractLogoutPath();

        List<CoffeeNetApp> profileApps = apps.get("profile");
        CoffeeNetApp profileApp = null;

        if (profileApps != null && !profileApps.isEmpty()) {
            profileApp = profileApps.get(0);
        }

        List<CoffeeNetApp> coffeeNetApps = apps.get("apps");

        return new CoffeeNetNavigationInformation(currentCoffeeNetUser, coffeeNetApps, profileApp, logoutPath,
                appInformation);
    }
}
