package rocks.coffeenet.legacy.autoconfigure.navigation;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.core.Is.is;


/**
 * @author  Tobias Schneider
 */
public class CurrentCoffeeNetUserTest {

    @Test
    public void getAvatarWithGravatar() {

        CurrentCoffeeNetUser currentCoffeeNetUser = new CurrentCoffeeNetUser("username", "username@coffeenet");
        assertThat(currentCoffeeNetUser.getAvatar(),
            is("https://gravatar.com/avatar/055d3a65cd9bad8911ed5fe877b842ab?size=64"));
    }
}
