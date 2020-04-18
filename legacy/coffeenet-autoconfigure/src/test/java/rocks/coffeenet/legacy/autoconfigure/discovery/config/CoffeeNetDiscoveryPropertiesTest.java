package rocks.coffeenet.legacy.autoconfigure.discovery.config;

import org.junit.Test;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertThat;


/**
 * @author  Tobias Schneider
 */
public class CoffeeNetDiscoveryPropertiesTest {

    @Test
    public void testDefaultValues() {

        CoffeeNetDiscoveryProperties sut = new CoffeeNetDiscoveryProperties();
        assertThat(sut.isEnabled(), is(true));
    }
}
