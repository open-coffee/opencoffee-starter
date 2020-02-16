package rocks.coffeenet.legacy.autoconfigure.security.config;

import org.junit.Test;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertThat;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetSecurityResourcePropertiesTest {

    @Test
    public void testDefaultValues() {

        CoffeeNetSecurityResourceProperties sut = new CoffeeNetSecurityResourceProperties();
        assertThat(sut.getId(), is("oauth2-resource"));
        assertThat(sut.getUserInfoUri(), is("http://localhost:9999/user"));
    }
}
