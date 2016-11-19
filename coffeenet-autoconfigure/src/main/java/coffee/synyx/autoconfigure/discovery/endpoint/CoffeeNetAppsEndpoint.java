package coffee.synyx.autoconfigure.discovery.endpoint;

import coffee.synyx.autoconfigure.discovery.service.App;
import coffee.synyx.autoconfigure.discovery.service.AppService;
import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;

import org.springframework.boot.actuate.endpoint.Endpoint;

import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * Prides a API to receive all registered CoffeeNet applications.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetAppsEndpoint implements Endpoint<List<App>> {

    private AppService appService;
    private CoffeeNetCurrentUserService coffeeNetCurrentUserService;

    public CoffeeNetAppsEndpoint(AppService appService, CoffeeNetCurrentUserService coffeeNetCurrentUserService) {

        this.appService = appService;
        this.coffeeNetCurrentUserService = coffeeNetCurrentUserService;
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
    public List<App> invoke() {

        return appService.getApps()
            .stream()
            .filter(app -> app.isAllowedToAccessBy(coffeeNetCurrentUserService.get()))
            .collect(toList());
    }
}
