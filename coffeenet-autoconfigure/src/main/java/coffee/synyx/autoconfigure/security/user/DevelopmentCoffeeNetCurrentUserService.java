package coffee.synyx.autoconfigure.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


/**
 * Development service with the two mock user."user" and "admin" to match the login data in
 * {@link coffee.synyx.autoconfigure.security.config.development.DevelopmentCoffeeNetWebSecurityConfigurerAdapter}.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class DevelopmentCoffeeNetCurrentUserService implements CoffeeNetCurrentUserService {

    @Override
    public CoffeeNetUserDetails get() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return getCoffeeNetUserDetails(user);
    }


    private static CoffeeNetUserDetails getCoffeeNetUserDetails(User user) {

        String username = user.getUsername();
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        return new HumanCoffeeNetUser(username, username + "@coffeenet", authorities);
    }
}
