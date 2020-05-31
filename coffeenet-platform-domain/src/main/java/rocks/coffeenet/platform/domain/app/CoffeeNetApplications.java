package rocks.coffeenet.platform.domain.app;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;


/**
 * A simple service interface providing a list of CoffeeNet applications known in the environment.
 *
 * <p>Implementations should make use of a discovery mechanism to determine the available applications.</p>
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
@FunctionalInterface
public interface CoffeeNetApplications {

    /**
     * A list of available CoffeeNet applications.
     *
     * <p>This does not filter the applications in any way. For filtered output,
     * {@link #getApplications(CoffeeNetApplicationQuery)} is provided as an alternative.</p>
     *
     * @return  the available applications.
     */
    List<CoffeeNetApplication> getApplications();


    /**
     * A list of available CoffeeNet applications, filtered on the provided query.
     *
     * <p>The query filters according to the authorities in {@link CoffeeNetApplicationQuery#getAuthorities()} and only
     * returns applications that would allow usage based on those authorities. Additionally
     * {@link CoffeeNetApplicationQuery#getNames()} will be used to restrict the returned result to only applications
     * from this list.</p>
     *
     * @param  query  a query, restricting the returned applications on name on allowed authorities.
     *
     * @return  the filtered available applications.
     */
    default List<CoffeeNetApplication> getApplications(CoffeeNetApplicationQuery query) {

        return getApplications()
                .stream()
            .filter(application -> isRequested(query, application))
            .filter(application -> isAllowed(query, application))
            .collect(Collectors.toList());
    }


    static boolean isRequested(CoffeeNetApplicationQuery query, CoffeeNetApplication application) {

        return query.getNames().isEmpty() || query.getNames().contains(application.getName());
    }


    static boolean isAllowed(CoffeeNetApplicationQuery query, CoffeeNetApplication application) {

        return application.getAuthorities().isEmpty()
            || application.getAuthorities().stream()
            .anyMatch(applicationAuthority ->
                    query.getAuthorities().stream()
                    .anyMatch(applicationAuthority::equalsIgnoreCase));
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
