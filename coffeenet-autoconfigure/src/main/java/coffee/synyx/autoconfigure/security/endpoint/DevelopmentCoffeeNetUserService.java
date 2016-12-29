package coffee.synyx.autoconfigure.security.endpoint;

import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.user.CoffeeNetUserDetails;


/**
 * Development user service with a mock user named "Coffy"
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  David Schilling - schilling@synyx.de
 */
public class DevelopmentCoffeeNetUserService implements CoffeeNetUserService {

    private final CoffeeNetCurrentUserService coffeeNetCurrentUserService;

    DevelopmentCoffeeNetUserService(CoffeeNetCurrentUserService coffeeNetCurrentUserService) {

        this.coffeeNetCurrentUserService = coffeeNetCurrentUserService;
    }

    @Override
    public CoffeeNetUser getUser() {

        CoffeeNetUserDetails coffeeNetUserDetails = coffeeNetCurrentUserService.get();

        return new CoffeeNetUser(coffeeNetUserDetails.getUsername(), coffeeNetUserDetails.getEmail());
    }
}
