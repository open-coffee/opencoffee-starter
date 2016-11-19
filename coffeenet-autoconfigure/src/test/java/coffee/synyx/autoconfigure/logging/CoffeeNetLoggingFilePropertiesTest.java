package coffee.synyx.autoconfigure.logging;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import static org.junit.Assert.assertThat;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class CoffeeNetLoggingFilePropertiesTest {

    @Test
    public void testDefaultValues() {

        CoffeeNetLoggingFileProperties sut = new CoffeeNetLoggingFileProperties();
        assertThat(sut.isEnabled(), is(nullValue()));
        assertThat(sut.getMaxFileSize(), is("10MB"));
        assertThat(sut.getFile(), is("logs/app.txt"));
        assertThat(sut.getRollingFNP(), is("logs/app.txt%i"));
        assertThat(sut.getPattern(), is("%d{yyyy-MM-dd HH:mm:ss.SSS} %5p --- [%t] %-40.40logger{39} : %m%n%wEx"));
    }
}
