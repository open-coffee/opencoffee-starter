package coffee.synyx.autoconfigure.logging;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import static org.junit.Assert.assertThat;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetLoggingConsolePropertiesTest {

    @Test
    public void testDefaultValues() {

        CoffeeNetLoggingConsoleProperties sut = new CoffeeNetLoggingConsoleProperties();
        assertThat(sut.isEnabled(), is(nullValue()));
    }
}
