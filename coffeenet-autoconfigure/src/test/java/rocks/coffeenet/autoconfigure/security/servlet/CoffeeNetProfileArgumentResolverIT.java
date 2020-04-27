package rocks.coffeenet.autoconfigure.security.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import rocks.coffeenet.autoconfigure.security.ProfileMapperTestConfiguration;

import rocks.coffeenet.test.app.servlet.MvcTestWebApplication;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@SpringBootTest(properties = "spring.main.web-application-type=servlet")
@ContextConfiguration(classes = { ProfileMapperTestConfiguration.class, MvcTestWebApplication.class })
@DisplayName("CoffeeNetProfileArgumentResolver")
class CoffeeNetProfileArgumentResolverIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void setup() {

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    @WithMockUser(value = "example")
    @DisplayName("should inject a profile into a controller if user is authenticated")
    void authenticatedUser() throws Exception {

        // FIXME: Find a good way to test this.

        mvc.perform(get("/with-profile"))
            .andExpect(jsonPath("profile.name", is("example")));
    }


    @Test
    @DisplayName("should not inject a profile into a controller if user is not authenticated")
    void noAuthenticatedUser() throws Exception {

        // FIXME: Find a good way to test this.

        mvc.perform(get("/with-profile"))
            .andExpect(jsonPath("profile", is(nullValue())));
    }
}
