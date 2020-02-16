package rocks.coffeenet.legacy.actuate.autoconfigure;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;


public class AuthServerHealthIndicatorConfigurationPropertiesTest {

    @Test
    public void testDefaultValues() {

        AuthServerHealthIndicatorConfigurationProperties sut = new AuthServerHealthIndicatorConfigurationProperties();
        assertThat(sut.getHealthUri(), is("http://localhost:9999/health"));
    }
}
