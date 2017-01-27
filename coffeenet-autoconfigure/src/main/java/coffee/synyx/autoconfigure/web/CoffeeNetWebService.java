package coffee.synyx.autoconfigure.web;

/**
 * Interface of the coffeenet web service.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
public interface CoffeeNetWebService {

    /**
     * Returns all information that are needed for the global CoffeeNet navigation bar.
     *
     * @return  the {@link CoffeeNetWeb}
     */
    CoffeeNetWeb get();
}
