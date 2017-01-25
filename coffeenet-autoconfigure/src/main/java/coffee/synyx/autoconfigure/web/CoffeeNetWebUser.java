package coffee.synyx.autoconfigure.web;

/**
 * Represents a coffeenet user.
 *
 * @author  Tobias Schneider - schneider@synyx.de
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
