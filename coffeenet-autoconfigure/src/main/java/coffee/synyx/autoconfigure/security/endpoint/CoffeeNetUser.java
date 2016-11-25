package coffee.synyx.autoconfigure.security.endpoint;

/**
 * Represents a coffeenet user.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public final class CoffeeNetUser {

    private final String username;
    private final String email;

    CoffeeNetUser(String username, String email) {

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
