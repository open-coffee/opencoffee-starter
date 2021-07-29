package rocks.coffeenet.autoconfigure.discovery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClientAutoConfiguration;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.cloud.commons.util.UtilAutoConfiguration;

import rocks.coffeenet.platform.domain.app.CoffeeNetApplications;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName("CoffeeNetDiscoveryAutoConfiguration")
class CoffeeNetDiscoveryAutoConfigurationTests {

    private WebApplicationContextRunner contextRunner;

    @BeforeEach
    void setupWebApplicationContext() {

        contextRunner =
            new WebApplicationContextRunner().withConfiguration(AutoConfigurations.of(
                    CoffeeNetDiscoveryAutoConfiguration.class, CompositeDiscoveryClientAutoConfiguration.class,
                    SimpleDiscoveryClientAutoConfiguration.class, CommonsClientAutoConfiguration.class,
                    UtilAutoConfiguration.class));
    }


    @Test
    @DisplayName("should not register application service if discovery not on classpath")
    void noDiscoveryPlatformOnClassPath() {

        contextRunner.withClassLoader(new FilteredClassLoader("rocks.coffeenet.platform.discovery")).run(context ->
                assertThat(context).doesNotHaveBean(CoffeeNetApplications.class));
    }


    @Test
    @DisplayName("should register application service if discovery client available")
    void discoveryClientAvailable() {

        contextRunner.run(context -> assertThat(context).hasSingleBean(CoffeeNetApplications.class));
    }
}
