package coffee.synyx.autoconfigure.user.endpoint;

import coffee.synyx.autoconfigure.user.service.User;
import coffee.synyx.autoconfigure.user.service.UserService;

import org.springframework.boot.actuate.endpoint.Endpoint;


/**
 * Provides information about the user.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class UserEndpoint implements Endpoint<User> {

    private final UserService userService;

    public UserEndpoint(UserService userService) {

        this.userService = userService;
    }

    @Override
    public String getId() {

        return "coffeenet/user";
    }


    @Override
    public boolean isEnabled() {

        return true;
    }


    @Override
    public boolean isSensitive() {

        return false;
    }


    @Override
    public User invoke() {

        return userService.getUser();
    }
}
