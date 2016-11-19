package coffee.synyx.autoconfigure.discovery.config;

import coffee.synyx.autoconfigure.discovery.endpoint.CoffeeNetAppsEndpoint;
import coffee.synyx.autoconfigure.discovery.service.AppService;
import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;


/**
 * Configuration abstraction for integrated or mocked service discovery configuration.
 *
 * @author  Yannic Klem - klem@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public interface CoffeeNetServiceDiscoveryConfiguration {

    /**
     * Provides the implementation behind the {@link AppService} interface to access the provided
     * {@link coffee.synyx.autoconfigure.discovery.service.App} from the development or integrated service layer.
     *
     * @return  {@link coffee.synyx.autoconfigure.discovery.service.EurekaAppService} when integration is activated,
     *          {@link coffee.synyx.autoconfigure.discovery.service.MockAppService} otherwise
     */
    AppService coffeeNetAppService();


    /**
     * Provides the {@link CoffeeNetAppsEndpoint} with different service implementation (development,integrated) of the
     * {@link AppService}.
     *
     * @param  coffeeNetCurrentUserService  Service to receive the actual user
     *
     * @return  {@link CoffeeNetAppsEndpoint} to provide the registered
     *          {@link coffee.synyx.autoconfigure.discovery.service.App}s
     */
    CoffeeNetAppsEndpoint coffeeNetAppsEndpoint(CoffeeNetCurrentUserService coffeeNetCurrentUserService);
}
