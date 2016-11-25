package coffee.synyx.autoconfigure.discovery.endpoint;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
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
public class CoffeeNetAppsEndpoint implements Endpoint<List<CoffeeNetApp>> {

    private CoffeeNetAppService coffeeNetAppService;
    private CoffeeNetCurrentUserService coffeeNetCurrentUserService;

    public CoffeeNetAppsEndpoint(CoffeeNetAppService coffeeNetAppService,
        CoffeeNetCurrentUserService coffeeNetCurrentUserService) {

        this.coffeeNetAppService = coffeeNetAppService;
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
    public List<CoffeeNetApp> invoke() {

        return coffeeNetAppService.getApps().stream().filter(app ->
                    app.isAllowedToAccessBy(coffeeNetCurrentUserService.get())).collect(toList());
    }
}
