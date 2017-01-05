package coffee.synyx.starter.web.javascript;

import coffee.synyx.starter.web.CoffeeNetWebService;
import coffee.synyx.starter.web.CoffeeNetWebUser;

import org.springframework.boot.actuate.endpoint.Endpoint;


/**
 * Provides information about the user.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetUserEndpoint implements Endpoint<CoffeeNetWebUser> {

    private final CoffeeNetWebService coffeeNetWebService;

    CoffeeNetUserEndpoint(CoffeeNetWebService coffeeNetWebService) {

        this.coffeeNetWebService = coffeeNetWebService;
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
    public CoffeeNetWebUser invoke() {

        return coffeeNetWebService.get().getCoffeeNetWebUser();
    }
}
