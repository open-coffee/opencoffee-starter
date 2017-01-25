package coffee.synyx.autoconfigure.security.service;

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
    CoffeeNetUserDetails get();
}
