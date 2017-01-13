package coffee.synyx.autoconfigure.web.web;

/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public interface CoffeeNetWebService {

    /**
     * Returns all information that are needed for the global CoffeeNet navigation bar.
     *
     * @return  the {@link CoffeeNetWeb}
     */
    CoffeeNetWeb get();
}
