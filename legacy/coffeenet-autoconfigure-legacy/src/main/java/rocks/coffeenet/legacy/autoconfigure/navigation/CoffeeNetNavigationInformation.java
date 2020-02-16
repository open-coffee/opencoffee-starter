package rocks.coffeenet.legacy.autoconfigure.navigation;

import rocks.coffeenet.legacy.autoconfigure.discovery.service.CoffeeNetApp;

import java.util.List;


/**
 * Data transfer object that provides all information for the frontend.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
public final class CoffeeNetNavigationInformation {

    private final CurrentCoffeeNetUser currentCoffeeNetUser;
    private final List<CoffeeNetApp> coffeeNetApps;
    private final CoffeeNetApp profileApp;
    private final String logoutPath;
    private final CoffeeNetNavigationAppInformation coffeeNetNavigationAppInformation;

    CoffeeNetNavigationInformation(CurrentCoffeeNetUser currentCoffeeNetUser, List<CoffeeNetApp> coffeeNetApps,
        CoffeeNetApp profileApp, String logoutPath,
        CoffeeNetNavigationAppInformation coffeeNetNavigationAppInformation) {

        this.currentCoffeeNetUser = currentCoffeeNetUser;
        this.coffeeNetApps = coffeeNetApps;
        this.profileApp = profileApp;
        this.logoutPath = logoutPath;
        this.coffeeNetNavigationAppInformation = coffeeNetNavigationAppInformation;
    }

    public CurrentCoffeeNetUser getCurrentCoffeeNetUser() {

        return currentCoffeeNetUser;
    }


    public List<CoffeeNetApp> getCoffeeNetApps() {

        return coffeeNetApps;
    }


    public CoffeeNetApp getProfileApp() {

        return profileApp;
    }


    public String getLogoutPath() {

        return logoutPath;
    }


    public CoffeeNetNavigationAppInformation getCoffeeNetNavigationAppInformation() {

        return coffeeNetNavigationAppInformation;
    }
}
