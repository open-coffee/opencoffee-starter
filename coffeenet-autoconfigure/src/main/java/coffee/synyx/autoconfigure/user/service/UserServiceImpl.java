package coffee.synyx.autoconfigure.user.service;

import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.user.CoffeeNetUserDetails;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  David Schilling - schilling@synyx.de
 */
public class UserServiceImpl implements UserService {

    private final CoffeeNetCurrentUserService coffeeNetCurrentUserService;

    @Autowired
    public UserServiceImpl(CoffeeNetCurrentUserService coffeeNetCurrentUserService) {

        this.coffeeNetCurrentUserService = coffeeNetCurrentUserService;
    }

    @Override
    public User getUser() {

        CoffeeNetUserDetails user = coffeeNetCurrentUserService.get();

        return new User(user.getUsername(), user.getEmail());
    }
}
