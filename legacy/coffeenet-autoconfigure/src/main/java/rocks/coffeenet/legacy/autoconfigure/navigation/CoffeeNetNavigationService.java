package rocks.coffeenet.legacy.autoconfigure.navigation;

/**
 * Interface of the coffeenet navigation service.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
public interface CoffeeNetNavigationService {

    /**
     * Returns all information that are needed for the global CoffeeNet navigation bar.
     *
     * @return  the {@link CoffeeNetNavigationInformation}
     */
    CoffeeNetNavigationInformation get();
}
