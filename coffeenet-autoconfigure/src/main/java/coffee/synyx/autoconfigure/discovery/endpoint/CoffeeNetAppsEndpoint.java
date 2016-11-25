package coffee.synyx.autoconfigure.discovery.endpoint;

import coffee.synyx.autoconfigure.discovery.service.App;
import coffee.synyx.autoconfigure.discovery.service.AppService;

import org.springframework.boot.actuate.endpoint.Endpoint;

import java.util.List;


/**
 * Prides a API to receive all registered CoffeeNet applications.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetAppsEndpoint implements Endpoint<List<App>> {

    private AppService appService;

    public CoffeeNetAppsEndpoint(AppService appService) {

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
    public List<App> invoke() {

        return appService.getApps();
    }
}
