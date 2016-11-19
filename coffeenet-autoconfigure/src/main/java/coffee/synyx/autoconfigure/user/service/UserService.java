package coffee.synyx.autoconfigure.user.service;

/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public interface UserService {

    /**
     * Returns the current user.
     *
     * @return  current {@link User}
     */
    User getUser();
}
