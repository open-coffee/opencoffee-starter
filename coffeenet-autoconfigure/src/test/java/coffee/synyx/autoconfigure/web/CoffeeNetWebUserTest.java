package coffee.synyx.autoconfigure.web;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.core.Is.is;


/**
 * @author  Tobias Schneider
 */
public class CoffeeNetWebUserTest {

    @Test
    public void getAvatarWithGravatar() {

        CoffeeNetWebUser coffeeNetWebUser = new CoffeeNetWebUser("username", "username@coffeenet");
        assertThat(coffeeNetWebUser.getAvatar(),
            is("https://gravatar.com/avatar/055d3a65cd9bad8911ed5fe877b842ab?size=64"));
    }
}
