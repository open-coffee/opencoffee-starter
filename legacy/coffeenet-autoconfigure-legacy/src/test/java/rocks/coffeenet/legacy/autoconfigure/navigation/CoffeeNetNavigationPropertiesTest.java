package rocks.coffeenet.legacy.autoconfigure.navigation;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import static org.junit.Assert.assertThat;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetNavigationPropertiesTest {

    @Test
    public void testDefaultValues() {

        CoffeeNetNavigationProperties sut = new CoffeeNetNavigationProperties();

        assertThat(sut.getLogoutPath(), is("/logout"));
        assertThat(sut.getProfileServiceName(), is("profile"));
        assertThat(sut.getDisplayInNavigationForRoles(), is(nullValue()));
        assertThat(sut.isDisplayVersions(), is(true));
    }
}
