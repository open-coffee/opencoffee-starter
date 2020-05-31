package rocks.coffeenet.platform.discovery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClient;

import org.springframework.test.context.ActiveProfiles;

import rocks.coffeenet.platform.domain.app.CoffeeNetApplication;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@EnableAutoConfiguration
@SpringBootTest
@ActiveProfiles("discovery")
@DisplayName("DiscoveryClientCoffeeNetApplications")
class DiscoveryClientCoffeeNetApplicationsTests {

    @Autowired
    SimpleDiscoveryClient discoveryClient;

    private DiscoveryClientCoffeeNetApplications coffeeNetApplications;

    @BeforeEach
    void setup() {

        coffeeNetApplications = new DiscoveryClientCoffeeNetApplications(discoveryClient);
    }


    @Test
    @DisplayName("retrieves the valid applications from the discovery client")
    void validApplications() {

        List<CoffeeNetApplication> applications = coffeeNetApplications.getApplications();

        assertThat(applications)
            .isNotEmpty()
            .hasSize(2)
            .extracting(CoffeeNetApplication::getName)
            .containsExactly("another-app", "example-app");
    }

    @SpringBootConfiguration
    public static class TestConfiguration {
    }
}
