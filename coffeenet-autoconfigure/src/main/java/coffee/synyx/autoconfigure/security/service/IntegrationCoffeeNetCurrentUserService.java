package coffee.synyx.autoconfigure.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  David Schilling - schilling@synyx.de
 */
public class IntegrationCoffeeNetCurrentUserService implements CoffeeNetCurrentUserService {

    @Override
    public CoffeeNetUserDetails get() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CoffeeNetUserDetails)) {
            return null;
        }

        return (CoffeeNetUserDetails) principal;
    }
}
