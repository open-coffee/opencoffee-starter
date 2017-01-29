package coffee.synyx.autoconfigure.discovery.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
    public Map<String, List<CoffeeNetApp>> getApps() {

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();

        apps.put("Coffee App",
            singletonList(new CoffeeNetApp("Coffee App", "https://coffee-app.coffeenet", new HashSet<>(emptyList()))));
        apps.put("Profile",
            singletonList(new CoffeeNetApp("Profile", "https://profile.coffeenet", new HashSet<>(emptyList()))));

        apps.put("Coffee Admin App",
            singletonList(
                new CoffeeNetApp("Coffee Admin App", "https://coffee-admin-app.coffeenet",
                    new HashSet<>(singletonList("ROLE_COFFEENET-ADMIN")))));

        return apps;
    }


    @Override
    public Map<String, List<CoffeeNetApp>> getApps(AppQuery query) {

        return getApps();
    }
}
