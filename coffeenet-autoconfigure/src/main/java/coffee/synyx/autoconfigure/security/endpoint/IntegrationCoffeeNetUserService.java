package coffee.synyx.autoconfigure.security.endpoint;

import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.user.CoffeeNetUserDetails;


/**
 * This integrative service provides the current logged in {@link CoffeeNetUser}.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  David Schilling - schilling@synyx.de
 */
public class IntegrationCoffeeNetUserService implements CoffeeNetUserService {

    private final CoffeeNetCurrentUserService coffeeNetCurrentUserService;

    IntegrationCoffeeNetUserService(CoffeeNetCurrentUserService coffeeNetCurrentUserService) {

        this.coffeeNetCurrentUserService = coffeeNetCurrentUserService;
    }

    @Override
    public CoffeeNetUser getUser() {

        CoffeeNetUserDetails user = coffeeNetCurrentUserService.get();

        return new CoffeeNetUser(user.getUsername(), user.getEmail());
    }
}
