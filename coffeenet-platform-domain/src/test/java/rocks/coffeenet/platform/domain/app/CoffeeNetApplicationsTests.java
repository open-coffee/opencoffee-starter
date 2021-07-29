package rocks.coffeenet.platform.domain.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName("CoffeeNetApplications")
class CoffeeNetApplicationsTests {

    private CoffeeNetApplication minimalTest;
    private CoffeeNetApplication rolesTest;
    private CoffeeNetApplication anotherTest;
    private List<CoffeeNetApplication> applications;

    @BeforeEach
    void setup() throws MalformedURLException {

        URL minimalTestUrl = URI.create("https://example.com/test/minimal").toURL();
        minimalTest = CoffeeNetApplication.withNameAndApplicationUrl("minimal", minimalTestUrl).build();

        URL rolesTestUrl = URI.create("https://example.com/test/roles").toURL();
        rolesTest = CoffeeNetApplication.withNameAndApplicationUrl("roles", rolesTestUrl)
                .withAuthorities("ROLE1", "ROLE2")
                .build();

        URL anotherTestUrl = URI.create("https://example.com/test/another").toURL();
        anotherTest = CoffeeNetApplication.withNameAndApplicationUrl("another", anotherTestUrl)
                .withAuthorities("ROLE1")
                .build();

        applications = Arrays.asList(minimalTest, rolesTest, anotherTest);
    }


    @Test
    @DisplayName("should return all applications with no authorities on empty query")
    void emptyQuery() {

        CoffeeNetApplications applications = () -> this.applications;
        CoffeeNetApplicationQuery query = CoffeeNetApplicationQuery.builder().build();

        assertThat(applications.getApplications(query))
            .isNotNull()
            .isNotEmpty()
            .containsExactlyInAnyOrder(minimalTest);
    }


    @Test
    @DisplayName("should return only named applications with no restrictions on name query")
    void namedApplications() {

        CoffeeNetApplications applications = () -> this.applications;
        CoffeeNetApplicationQuery query = CoffeeNetApplicationQuery.builder()
                .withNames("minimal", "another")
                .build();

        assertThat(applications.getApplications(query))
            .isNotNull()
            .isNotEmpty()
            .containsOnly(minimalTest);
    }


    @Test
    @DisplayName("should return applications with matching authorities (subset)")
    void withAuthoritiesNotMatching() {

        CoffeeNetApplications applications = () -> this.applications;
        CoffeeNetApplicationQuery query = CoffeeNetApplicationQuery.builder()
                .withAuthorities("ROLE2")
                .build();

        assertThat(applications.getApplications(query))
            .isNotNull()
            .isNotEmpty()
            .containsExactlyInAnyOrder(minimalTest, rolesTest);
    }
}
