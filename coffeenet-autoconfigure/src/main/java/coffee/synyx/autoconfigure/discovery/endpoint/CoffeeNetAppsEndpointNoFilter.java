package coffee.synyx.autoconfigure.discovery.endpoint;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;

import org.springframework.boot.actuate.endpoint.Endpoint;

import java.util.List;


/**
 * Prides a API to receive all registered CoffeeNet applications.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetAppsEndpointNoFilter implements Endpoint<List<CoffeeNetApp>> {

    private final CoffeeNetAppService appService;

    public CoffeeNetAppsEndpointNoFilter(CoffeeNetAppService appService) {

        this.appService = appService;
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

        return appService.getApps();
    }
}
