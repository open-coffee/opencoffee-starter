package coffee.synyx.autoconfigure.security.config;

import coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties;
import org.junit.Test;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.Profile.DEVELOPMENT;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
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