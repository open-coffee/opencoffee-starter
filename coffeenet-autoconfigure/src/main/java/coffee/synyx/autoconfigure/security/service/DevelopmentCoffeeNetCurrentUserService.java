package coffee.synyx.autoconfigure.security.service;

import coffee.synyx.autoconfigure.security.config.DevelopmentCoffeeNetWebSecurityConfigurerAdapter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Optional;


/**
 * Development service with the two mock user."user" and "admin" to match the login data in
 * {@link DevelopmentCoffeeNetWebSecurityConfigurerAdapter}.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class DevelopmentCoffeeNetCurrentUserService implements CoffeeNetCurrentUserService {

    @Override
    public Optional<CoffeeNetUserDetails> get() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return getCoffeeNetUserDetails(user);
    }


    private static Optional<CoffeeNetUserDetails> getCoffeeNetUserDetails(User user) {

        String username = user.getUsername();
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        return Optional.of(new HumanCoffeeNetUser(username, username + "@coffeenet", authorities));
    }
}
