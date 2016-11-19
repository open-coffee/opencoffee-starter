package coffee.synyx.autoconfigure.user.service;

import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @author  David Schilling - schilling@synyx.de
 */
public class MockUserServiceImpl implements UserService {

    @Override
    public User getUser() {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        return new User(name, name + "@synyx.de");
    }
}
