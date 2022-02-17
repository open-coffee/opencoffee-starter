package rocks.coffeenet.legacy.autoconfigure.discovery.service;

import java.util.List;
import java.util.Map;


/**
 * Service to provide all registered apps via the service discovery.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public interface CoffeeNetAppService {

    /**
     * Returns a case insensitive key map of all registered applications from the service discovery.
     *
     * @return  a map with the name of the {@link CoffeeNetApp} as key and a list of {@link CoffeeNetApp} as value
     */
    Map<String, List<CoffeeNetApp>> getApps();


    /**
     * Returns an case insensitive key map of {@link java.util.TreeSet} filled with {@link CoffeeNetApp} that matches
     * the given {@link AppQuery}. Case insensitive to find "Frontpage" if you searched for "frontPage" e.g.
     *
     * @param  query  to filter the {@link CoffeeNetApp}s
     *
     * @return  a filtered map with the name of the {@link CoffeeNetApp} as key and a list of {@link CoffeeNetApp} as
     *          value
     */
    Map<String, List<CoffeeNetApp>> getApps(AppQuery query);
}
