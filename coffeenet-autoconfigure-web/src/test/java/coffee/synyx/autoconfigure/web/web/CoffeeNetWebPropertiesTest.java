package coffee.synyx.autoconfigure.web.web;

import org.junit.Test;

import static org.hamcrest.Matchers.is;

import static org.junit.Assert.assertThat;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetWebPropertiesTest {

    @Test
    public void testDefaultValues() {

        CoffeeNetWebProperties sut = new CoffeeNetWebProperties();

        assertThat(sut.getLogoutPath(), is("/logout"));
        assertThat(sut.getProfileServiceName(), is("profile"));
    }
}
