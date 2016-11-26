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
public class CoffeeNetAppsFilterEndpoint implements Endpoint<List<CoffeeNetApp>> {

    private CoffeeNetAppService appService;
    private CoffeeNetCurrentUserService currentUserService;

    public CoffeeNetAppsFilterEndpoint(CoffeeNetAppService appService, CoffeeNetCurrentUserService currentUserService) {

        this.appService = appService;
        this.currentUserService = currentUserService;
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

        return appService.getApps().stream().filter(app ->
                    app.isAllowedToAccessBy(currentUserService.get().getAuthoritiesAsString())).collect(toList());
    }
}
