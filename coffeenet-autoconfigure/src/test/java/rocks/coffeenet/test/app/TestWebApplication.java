package rocks.coffeenet.test.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import rocks.coffeenet.platform.domain.profile.CoffeeNetProfile;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@SpringBootApplication
@Controller
public class TestWebApplication {

    @GetMapping("/with-profile")
    public String withProfile(Model model, CoffeeNetProfile profile) {

        model.addAttribute("profile", profile);

        return "result";
    }

    @Configuration
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests().anyRequest().permitAll();
        }
    }
}
