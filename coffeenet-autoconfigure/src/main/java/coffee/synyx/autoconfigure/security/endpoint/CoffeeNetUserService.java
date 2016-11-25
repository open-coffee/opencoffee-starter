package coffee.synyx.autoconfigure.security.endpoint;

/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public interface CoffeeNetUserService {

    /**
     * Returns the current {@code CoffeeNet} user.
     *
     * @return  current {@link CoffeeNetUser}
     */
    CoffeeNetUser getUser();
}
