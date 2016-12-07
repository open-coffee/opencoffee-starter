package coffee.synyx.autoconfigure.logging;

import org.junit.Test;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertThat;


/**
 * @author  Tobias Schneider
 */
public class CoffeeNetLoggingPropertiesTest {

    @Test
    public void testDefaultValues() {

        CoffeeNetLoggingProperties sut = new CoffeeNetLoggingProperties();
        assertThat(sut.isEnabled(), is(true));
    }
}
