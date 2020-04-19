package rocks.coffeenet.platform.domain.app;

import java.util.List;
import java.util.function.Supplier;


/**
 * A simple service interface providing a list of CoffeeNet applications known in the environment.
 *
 * <p>Implementations should make use of a discovery mechanism to determine the available applications.</p>
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@FunctionalInterface
public interface CoffeeNetApplications extends Supplier<List<CoffeeNetApplication>> {


    default List<CoffeeNetApplication> getApplications() {

        return get();
    }

    /**
     * A simple functional interface to return the running application as a {@link CoffeeNetApplication}.
     *
     * <p>This should never return {@code null} and should always point to the running application.</p>
     */
    @FunctionalInterface
    interface Current extends Supplier<CoffeeNetApplication> {
    }
}
