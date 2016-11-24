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
        assertThat(sut.getMaxHistory(), is(30));
        assertThat(sut.getFile(), is("logs/app.log"));
        assertThat(sut.getFileNamePattern(), is("logs/app-%d{yyyy-MM-dd}.log"));
        assertThat(sut.getPattern(), is("%d{yyyy-MM-dd HH:mm:ss.SSS} %5p --- [%t] %-40.40logger{39} : %m%n%wEx"));
    }
}
