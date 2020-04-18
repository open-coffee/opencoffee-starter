package rocks.coffeenet.legacy.autoconfigure.navigation;

import org.springframework.boot.actuate.endpoint.Endpoint;


/**
 * Provides information that are needed.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
public class CoffeeNetNavigationEndpoint implements Endpoint<CoffeeNetNavigationInformation> {

    private final CoffeeNetNavigationService coffeeNetNavigationService;

    CoffeeNetNavigationEndpoint(CoffeeNetNavigationService coffeeNetNavigationService) {

        this.coffeeNetNavigationService = coffeeNetNavigationService;
    }

    @Override
    public String getId() {

        return "coffeenet/navigation";
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
    public CoffeeNetNavigationInformation invoke() {

        return coffeeNetNavigationService.get();
    }
}
