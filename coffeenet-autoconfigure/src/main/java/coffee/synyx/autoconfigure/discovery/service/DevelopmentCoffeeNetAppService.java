package coffee.synyx.autoconfigure.discovery.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;


/**
 * This implementation provides all registered CoffeeNet applications by the eureka service discovery.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class DevelopmentCoffeeNetAppService implements CoffeeNetAppService {

    @Override
    public List<CoffeeNetApp> getApps() {

        List<CoffeeNetApp> coffeeNetApps = new ArrayList<>();

        coffeeNetApps.add(new CoffeeNetApp("Coffee App", "https://coffee-app.coffeenet", new HashSet<>(emptyList())));
        coffeeNetApps.add(new CoffeeNetApp("Profile", "https://profile.coffeenet", new HashSet<>(emptyList())));
        coffeeNetApps.add(new CoffeeNetApp("CoffeeNet Admin App", "https://coffee-admin-app.coffeenet",
                new HashSet<>(singletonList("COFFEENET-ADMIN"))));

        return coffeeNetApps;
    }
}
