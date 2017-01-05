package coffee.synyx.starter.web;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;

import java.util.Collection;


public final class CoffeeNetWeb {

    private final CoffeeNetWebUser coffeeNetWebUser;
    private final Collection<CoffeeNetApp> coffeeNetApps;
    private final CoffeeNetApp profileApp;
    private final String logoutPath;

    public CoffeeNetWeb(CoffeeNetWebUser coffeeNetWebUser, Collection<CoffeeNetApp> coffeeNetApps,
        CoffeeNetApp profileApp, String logoutPath) {

        this.coffeeNetWebUser = coffeeNetWebUser;
        this.coffeeNetApps = coffeeNetApps;
        this.profileApp = profileApp;
        this.logoutPath = logoutPath;
    }

    public CoffeeNetWebUser getCoffeeNetWebUser() {

        return coffeeNetWebUser;
    }


    public Collection<CoffeeNetApp> getCoffeeNetApps() {

        return coffeeNetApps;
    }


    public CoffeeNetApp getProfileApp() {

        return profileApp;
    }


    public String getLogoutPath() {

        return logoutPath;
    }
}
