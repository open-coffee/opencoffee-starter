package coffee.synyx.autoconfigure.security.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.HashSet;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;


/**
 * Development service with the two mock user."user" and "admin" to match the login data in
 * {@link coffee.synyx.autoconfigure.security.config.development.DevelopmentCoffeeNetWebSecurityConfigurerAdapter}.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class DevelopmentCoffeeNetCurrentUserService implements CoffeeNetCurrentUserService {

    @Override
    public CoffeeNetUserDetails get() {

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        switch (principal.getUsername()) {
            case "user":
                return getUser();

            case "admin":
            default:
                return getAdmin();
        }
    }


    private CoffeeNetUserDetails getAdmin() {

        return new HumanCoffeeNetUser("admin", "admin@coffeenet",
                new HashSet<>(singleton(new SimpleGrantedAuthority("COFFEENET-ADMIN"))));
    }


    private static CoffeeNetUserDetails getUser() {

        return new HumanCoffeeNetUser("user", "user@coffeenet", emptySet());
    }
}
