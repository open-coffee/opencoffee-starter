package coffee.synyx.autoconfigure.logging;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import static org.junit.Assert.assertThat;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetLoggingGelfPropertiesTest {

    @Test
    public void testDefaultValues() {

        CoffeeNetLoggingGelfProperties sut = new CoffeeNetLoggingGelfProperties();

        assertThat(sut.isEnabled(), is(nullValue()));
        assertThat(sut.getServer(), is("localhost"));
        assertThat(sut.getEnvironment(), is(nullValue()));
        assertThat(sut.getPort(), is(12201));
        assertThat(sut.getProtocol(), is("UDP"));
        assertThat(sut.getLayout(), is("%m %n"));
    }
}
