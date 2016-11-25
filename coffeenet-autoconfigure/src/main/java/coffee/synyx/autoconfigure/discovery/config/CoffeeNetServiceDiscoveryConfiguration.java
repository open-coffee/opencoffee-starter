package coffee.synyx.autoconfigure.discovery.config;

import coffee.synyx.autoconfigure.discovery.endpoint.CoffeeNetAppsEndpoint;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.discovery.service.DevelopmentCoffeeNetAppService;
import coffee.synyx.autoconfigure.discovery.service.IntegrationEurekaCoffeeNetAppService;
import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;


/**
 * Configuration abstraction for integrated or mocked service discovery configuration.
 *
 * @author  Yannic Klem - klem@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public interface CoffeeNetServiceDiscoveryConfiguration {

    /**
     * Provides the implementation behind the {@link CoffeeNetAppService} interface to access the provided
     * {@link CoffeeNetApp} from the development or integrated service layer.
     *
     * @return  {@link IntegrationEurekaCoffeeNetAppService} when integration is activated,
     *          {@link DevelopmentCoffeeNetAppService} otherwise
     */
    CoffeeNetAppService coffeeNetAppService();


    /**
     * Provides the {@link CoffeeNetAppsEndpoint} with different service implementation (development,integrated) of the
     * {@link CoffeeNetAppService}.
     *
     * @param  coffeeNetCurrentUserService  Service to receive the actual user
     *
     * @return  {@link CoffeeNetAppsEndpoint} to provide the registered {@link CoffeeNetApp}s
     */
    CoffeeNetAppsEndpoint coffeeNetAppsEndpoint(CoffeeNetCurrentUserService coffeeNetCurrentUserService);
}
