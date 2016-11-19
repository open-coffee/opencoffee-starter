package coffee.synyx.autoconfigure.user.service;

/**
 * Represents a coffeenet user.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public final class User {

    private final String username;
    private final String email;

    public User(String username, String email) {

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
