package coffee.synyx.autoconfigure.navigation;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Provides information that are needed.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
@Controller
class CoffeeNetNavigationEndpoint {

    private final CoffeeNetNavigationService coffeeNetNavigationService;

    CoffeeNetNavigationEndpoint(CoffeeNetNavigationService coffeeNetNavigationService) {

        this.coffeeNetNavigationService = coffeeNetNavigationService;
    }

    @GetMapping("coffeenet/navigation")
    CoffeeNetNavigationInformation getNavigationInformation() {

        return coffeeNetNavigationService.get();
    }
}
