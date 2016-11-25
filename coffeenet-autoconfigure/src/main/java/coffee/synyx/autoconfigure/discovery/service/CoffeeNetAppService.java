package coffee.synyx.autoconfigure.discovery.service;

import java.util.List;


/**
 * Service to provide all registered apps via the service discovery.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public interface CoffeeNetAppService {

    /**
     * Returns a list of all registered applications from the service discovery.
     *
     * @return  List of {@link CoffeeNetApp}
     */
    List<CoffeeNetApp> getApps();
}
