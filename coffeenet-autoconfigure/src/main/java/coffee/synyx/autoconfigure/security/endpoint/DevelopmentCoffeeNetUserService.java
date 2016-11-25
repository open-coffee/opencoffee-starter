package coffee.synyx.autoconfigure.security.endpoint;

/**
 * Development user service with a mock user named "Coffy"
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  David Schilling - schilling@synyx.de
 */
public class DevelopmentCoffeeNetUserService implements CoffeeNetUserService {

    @Override
    public CoffeeNetUser getUser() {

        String name = "Coffy";

        return new CoffeeNetUser(name, name + "@felinepredator.net");
    }
}
