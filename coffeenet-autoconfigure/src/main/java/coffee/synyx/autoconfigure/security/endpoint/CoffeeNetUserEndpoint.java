package coffee.synyx.autoconfigure.security.endpoint;

import org.springframework.boot.actuate.endpoint.Endpoint;


/**
 * Provides information about the user.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetUserEndpoint implements Endpoint<CoffeeNetUser> {

    private final CoffeeNetUserService coffeeNetUserService;

    public CoffeeNetUserEndpoint(CoffeeNetUserService coffeeNetUserService) {

        this.coffeeNetUserService = coffeeNetUserService;
    }

    @Override
    public String getId() {

        return "coffeenet/user";
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
    public CoffeeNetUser invoke() {

        return coffeeNetUserService.getUser();
    }
}
