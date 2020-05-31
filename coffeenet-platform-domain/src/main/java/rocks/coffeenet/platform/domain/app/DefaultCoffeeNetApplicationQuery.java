package rocks.coffeenet.platform.domain.app;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;


/**
 * Simple default implementation of {@link CoffeeNetApplicationQuery}. Instances are created only via a builder pattern
 * with {@link CoffeeNetApplicationQuery.Builder}.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
class DefaultCoffeeNetApplicationQuery implements CoffeeNetApplicationQuery {

    private final Set<String> names = new TreeSet<>();
    private final Set<String> authorities = new TreeSet<>();

    DefaultCoffeeNetApplicationQuery(Set<String> names, Set<String> authorities) {

        this.names.addAll(names);
        this.authorities.addAll(authorities);
    }

    @Override
    public Set<String> getNames() {

        return Collections.unmodifiableSet(names);
    }


    @Override
    public Set<String> getAuthorities() {

        return Collections.unmodifiableSet(authorities);
    }
}
