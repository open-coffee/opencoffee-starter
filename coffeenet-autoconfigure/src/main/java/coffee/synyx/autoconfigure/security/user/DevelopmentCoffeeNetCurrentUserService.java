package coffee.synyx.autoconfigure.security.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;

import static java.util.Collections.singleton;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  David Schilling - schilling@synyx.de
 */
public class DevelopmentCoffeeNetCurrentUserService implements CoffeeNetCurrentUserService {

    @Override
    public CoffeeNetUserDetails get() {

        return new HumanCoffeeNetUser("development", "development@coffeenet.org",
                new HashSet<>(singleton(new SimpleGrantedAuthority("CoffeeNet-Admin"))));
    }
}
