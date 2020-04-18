package rocks.coffeenet.legacy.autoconfigure.security.config;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertThat;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetSecurityPropertiesTest {

    @Test
    public void testDefaultValues() {

        CoffeeNetSecurityProperties sut = new CoffeeNetSecurityProperties();
        assertThat(sut.isEnabled(), is(true));
        assertThat(sut.getDefaultLoginSuccessUrl(), is(nullValue()));
        assertThat(sut.getLogoutSuccessUrl(), is("http://localhost:9999/logout"));
    }
}
