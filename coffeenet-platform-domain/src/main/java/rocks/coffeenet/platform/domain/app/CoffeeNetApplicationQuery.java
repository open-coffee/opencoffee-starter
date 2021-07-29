package rocks.coffeenet.platform.domain.app;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;


/**
 * A filter query for {@link CoffeeNetApplications#getApplications(CoffeeNetApplicationQuery)}.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
public interface CoffeeNetApplicationQuery {

    /**
     * Application names to filter for.
     *
     * @return  a set of application names.
     */
    Set<String> getNames();


    /**
     * Authorities to filter for.
     *
     * @return  a set of authorities.
     *
     * @deprecated  2.0.0
     */
    @Deprecated
    Set<String> getAuthorities();


    static Builder builder() {

        return new Builder();
    }

    class Builder {

        private final Set<String> names = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        private final Set<String> authorities = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        public Builder withName(String name) {

            this.names.add(name);

            return this;
        }


        public Builder withNames(String... names) {

            return withNames(Arrays.asList(names));
        }


        public Builder withNames(Collection<String> names) {

            for (String authority : Objects.requireNonNull(names)) {
                withName(authority);
            }

            return this;
        }


        public Builder withAuthority(String authority) {

            this.authorities.add(authority);

            return this;
        }


        public Builder withAuthorities(String... authorities) {

            return withAuthorities(Arrays.asList(authorities));
        }


        public Builder withAuthorities(Collection<String> authorities) {

            for (String authority : Objects.requireNonNull(authorities)) {
                withAuthority(authority);
            }

            return this;
        }


        public CoffeeNetApplicationQuery build() {

            return new DefaultCoffeeNetApplicationQuery(names, authorities);
        }
    }
}
