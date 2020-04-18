package rocks.coffeenet.legacy.autoconfigure.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  David Schilling - schilling@synyx.de
 */
public class IntegrationCoffeeNetCurrentUserService implements CoffeeNetCurrentUserService {

    @Override
    public Optional<CoffeeNetUserDetails> get() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CoffeeNetUserDetails)) {
            return Optional.empty();
        }

        return Optional.of((CoffeeNetUserDetails) principal);
    }
}
