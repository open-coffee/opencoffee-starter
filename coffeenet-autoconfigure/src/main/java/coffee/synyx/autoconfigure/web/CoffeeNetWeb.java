package coffee.synyx.autoconfigure.web;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;

import java.util.List;


/**
 * Data transfer object that provides all information for the frontend.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
public final class CoffeeNetWeb {

    private final CoffeeNetWebUser coffeeNetWebUser;
    private final List<CoffeeNetApp> coffeeNetApps;
    private final CoffeeNetApp profileApp;
    private final String logoutPath;

    public CoffeeNetWeb(CoffeeNetWebUser coffeeNetWebUser, List<CoffeeNetApp> coffeeNetApps, CoffeeNetApp profileApp,
        String logoutPath) {

        this.coffeeNetWebUser = coffeeNetWebUser;
        this.coffeeNetApps = coffeeNetApps;
        this.profileApp = profileApp;
        this.logoutPath = logoutPath;
    }

    public CoffeeNetWebUser getCoffeeNetWebUser() {

        return coffeeNetWebUser;
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
}
