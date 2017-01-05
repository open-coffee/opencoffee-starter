package coffee.synyx.starter.web.javascript;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;

import coffee.synyx.starter.web.CoffeeNetWebService;

import org.springframework.boot.actuate.endpoint.Endpoint;

import java.util.Collection;


/**
 * Prides a API to receive all registered CoffeeNet applications.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetAppsEndpoint implements Endpoint<Collection<CoffeeNetApp>> {

    private final CoffeeNetWebService coffeeNetWebService;

    CoffeeNetAppsEndpoint(CoffeeNetWebService coffeeNetWebService) {

        this.coffeeNetWebService = coffeeNetWebService;
    }

    @Override
    public String getId() {

        return "coffeenet/apps";
    }


    @Override
    public boolean isEnabled() {

        return true;
    }


    @Override
    public boolean isSensitive() {

        return false;
    }


    @Override
    public Collection<CoffeeNetApp> invoke() {

        return coffeeNetWebService.get().getCoffeeNetApps();
    }
}
