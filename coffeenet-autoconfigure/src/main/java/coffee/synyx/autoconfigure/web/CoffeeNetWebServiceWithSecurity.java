package coffee.synyx.autoconfigure.web;

import coffee.synyx.autoconfigure.security.service.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.service.CoffeeNetUserDetails;


/**
 * This service exposes all information that are needed for the coffeenet navigation bar based in the server and client
 * side rendering engines like thymeleaf or javascript.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
public class CoffeeNetWebServiceWithSecurity implements CoffeeNetWebService {

    private final CoffeeNetCurrentUserService coffeeNetCurrentUserService;
    private final CoffeeNetWebProperties coffeeNetWebProperties;

    CoffeeNetWebServiceWithSecurity(CoffeeNetCurrentUserService coffeeNetCurrentUserService,
        CoffeeNetWebProperties coffeeNetWebProperties) {

        this.coffeeNetCurrentUserService = coffeeNetCurrentUserService;
        this.coffeeNetWebProperties = coffeeNetWebProperties;
    }

    @Override
    public CoffeeNetWeb get() {

        // logout path
        String logoutPath = coffeeNetWebProperties.getLogoutPath();

        // user
        CoffeeNetUserDetails coffeeNetUserDetails = coffeeNetCurrentUserService.get().get();
        CoffeeNetWebUser coffeeNetWebUser = new CoffeeNetWebUser(coffeeNetUserDetails.getUsername(),
                coffeeNetUserDetails.getEmail());

        return new CoffeeNetWeb(coffeeNetWebUser, null, null, logoutPath);
    }
}
