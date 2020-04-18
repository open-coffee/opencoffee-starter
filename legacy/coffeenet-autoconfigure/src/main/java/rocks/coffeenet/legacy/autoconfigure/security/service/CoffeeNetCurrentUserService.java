package rocks.coffeenet.legacy.autoconfigure.security.service;

import java.util.Optional;


/**
 * Interface for getting user information.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public interface CoffeeNetCurrentUserService {

    /**
     * Get the currently logged in user.
     *
     * @return  object with user information.
     */
    Optional<CoffeeNetUserDetails> get();
}
