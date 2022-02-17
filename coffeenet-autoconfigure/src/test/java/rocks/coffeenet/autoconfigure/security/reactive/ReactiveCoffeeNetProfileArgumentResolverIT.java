package rocks.coffeenet.autoconfigure.security.reactive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import rocks.coffeenet.autoconfigure.security.CoffeeNetProfileAutoConfiguration;
import rocks.coffeenet.autoconfigure.security.ProfileMapperTestConfiguration;

import rocks.coffeenet.test.app.reactive.ReactiveTestWebApplication;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@WebFluxTest
@ContextConfiguration(
    classes = {
        ProfileMapperTestConfiguration.class, ReactiveTestWebApplication.class, CoffeeNetProfileAutoConfiguration.class
    }
)
@DisplayName("ReactiveCoffeeNetProfileArgumentResolver")
class ReactiveCoffeeNetProfileArgumentResolverIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @WithMockUser(value = "example")
    @DisplayName("should inject a profile into a controller if user is authenticated")
    void authenticatedUser() throws Exception {

        // FIXME: Find a good way to test this.

        webTestClient.get()
            .uri("/with-profile")
            .exchange()
            .expectStatus().isOk()
            .expectBody().jsonPath("profile.name", "example");
    }


    @Test
    @DisplayName("should not inject a profile into a controller if user is not authenticated")
    void noAuthenticatedUser() throws Exception {

        // FIXME: Find a good way to test this.

        webTestClient.get()
            .uri("/with-profile")
            .exchange()
            .expectStatus().isOk()
            .expectBody().jsonPath("profile.name", is(nullValue()));
    }
}
