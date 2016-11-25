package coffee.synyx.autoconfigure.discovery.config.integration;

import coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetDiscoveryInstancePropertiesTest {

    @Test
    public void defaultValues() {

        InetUtils inetUtils = new InetUtils(new InetUtilsProperties());
        ServerProperties serverProperties = new ServerProperties();
        CoffeeNetConfigurationProperties coffeeNetConfigurationProperties = new CoffeeNetConfigurationProperties();

        CoffeeNetDiscoveryInstanceProperties sut = new CoffeeNetDiscoveryInstanceProperties(inetUtils, serverProperties,
                coffeeNetConfigurationProperties);

        assertThat(sut.getNonSecurePort(), is(8080));
        assertThat(sut.getHostname(), is("localhost"));
    }


    @Test
    public void setServerPort() {

        int port = 9999;

        ServerProperties serverProperties = new ServerProperties();
        serverProperties.setPort(port);

        CoffeeNetDiscoveryInstanceProperties sut = new CoffeeNetDiscoveryInstanceProperties(new InetUtils(
                    new InetUtilsProperties()), serverProperties, new CoffeeNetConfigurationProperties());

        assertThat(sut.getNonSecurePort(), is(port));
    }


    @Test
    public void applicationName() {

        String brandNewApplicationName = "BrandNewApplicationName";

        CoffeeNetConfigurationProperties coffeeNetConfigurationProperties = new CoffeeNetConfigurationProperties();
        coffeeNetConfigurationProperties.setApplicationName(brandNewApplicationName);

        CoffeeNetDiscoveryInstanceProperties sut = new CoffeeNetDiscoveryInstanceProperties(new InetUtils(
                    new InetUtilsProperties()), new ServerProperties(), coffeeNetConfigurationProperties);

        sut.afterPropertiesSet();

        assertThat(sut.getAppname(), is(brandNewApplicationName));
        assertThat(sut.getVirtualHostName(), is(brandNewApplicationName));
        assertThat(sut.getSecureVirtualHostName(), is(brandNewApplicationName));
    }


    @Test
    public void applicationNameIsUnknown() {

        CoffeeNetConfigurationProperties coffeeNetConfigurationProperties = new CoffeeNetConfigurationProperties();
        coffeeNetConfigurationProperties.setApplicationName("");

        CoffeeNetDiscoveryInstanceProperties sut = new CoffeeNetDiscoveryInstanceProperties(new InetUtils(
                    new InetUtilsProperties()), new ServerProperties(), coffeeNetConfigurationProperties);

        sut.afterPropertiesSet();

        String unknownApplicationName = "unknown";
        assertThat(sut.getAppname(), is(unknownApplicationName));
        assertThat(sut.getVirtualHostName(), is(unknownApplicationName));
        assertThat(sut.getSecureVirtualHostName(), is(unknownApplicationName));
    }
}
