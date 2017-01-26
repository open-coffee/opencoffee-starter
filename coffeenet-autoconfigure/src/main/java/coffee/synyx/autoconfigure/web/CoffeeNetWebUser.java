package coffee.synyx.autoconfigure.web;

/**
 * Represents a CoffeeNet user.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
public final class CoffeeNetWebUser {

    private final String username;
    private final String email;

    public CoffeeNetWebUser(String username, String email) {

        this.username = username;
        this.email = email;
    }

    public String getUsername() {

        return username;
    }


    public String getEmail() {

        return email;
    }
}
