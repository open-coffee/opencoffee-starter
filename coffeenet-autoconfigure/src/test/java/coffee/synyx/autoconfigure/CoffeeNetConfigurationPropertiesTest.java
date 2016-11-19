package coffee.synyx.autoconfigure;

import org.junit.Test;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.Profile.DEVELOPMENT;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import static org.junit.Assert.assertThat;


/**
 * @author  Tobias Schneider
 */
public class CoffeeNetConfigurationPropertiesTest {

    @Test
    public void testDefaultValues() {

        CoffeeNetConfigurationProperties sut = new CoffeeNetConfigurationProperties();

        assertThat(CoffeeNetConfigurationProperties.DEVELOPMENT, is("development"));
        assertThat(CoffeeNetConfigurationProperties.INTEGRATION, is("integration"));
        assertThat(sut.getApplicationName(), is(nullValue()));
        assertThat(sut.getProfile(), is(DEVELOPMENT));
    }
}
