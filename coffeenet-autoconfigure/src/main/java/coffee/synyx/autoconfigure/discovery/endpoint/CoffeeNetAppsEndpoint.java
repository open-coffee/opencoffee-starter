package coffee.synyx.autoconfigure.discovery.endpoint;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;

import org.springframework.boot.actuate.endpoint.Endpoint;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;


/**
 * Prides a API to receive all registered CoffeeNet applications.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetAppsEndpoint implements Endpoint<List<CoffeeNetApp>> {

    private CoffeeNetAppService appService;
    private Set<String> authorities;

    public CoffeeNetAppsEndpoint(CoffeeNetAppService appService, Set<String> authorities) {

        this.appService = appService;
        this.authorities = authorities;
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

        return appService.getApps().stream().filter(app -> app.isAllowedToAccessBy(authorities)).collect(toList());
    }
}
