package coffee.synyx.autoconfigure.web;

import org.springframework.boot.actuate.endpoint.Endpoint;


/**
 * Provides information that are needed.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
public class CoffeeNetWebEndpoint implements Endpoint<CoffeeNetWeb> {

    private final CoffeeNetWebService coffeeNetWebService;

    CoffeeNetWebEndpoint(CoffeeNetWebService coffeeNetWebService) {

        this.coffeeNetWebService = coffeeNetWebService;
    }

    @Override
    public String getId() {

        return "coffeenet/web";
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
    public CoffeeNetWeb invoke() {

        return coffeeNetWebService.get();
    }
}
