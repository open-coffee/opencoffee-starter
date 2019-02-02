package rocks.coffeenet.autoconfigure.security.config;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Tobias Schneider
 */
public class CoffeeNetSecurityClientPropertiesTest {

    @Test
    public void testDefaultValues() {

        CoffeeNetSecurityClientProperties sut = new CoffeeNetSecurityClientProperties();

        assertThat(sut.getAccessTokenUri(), is("http://localhost:9999/oauth/token"));
        assertThat(sut.getUserAuthorizationUri(), is("http://localhost:9999/oauth/authorize"));
    }

}
