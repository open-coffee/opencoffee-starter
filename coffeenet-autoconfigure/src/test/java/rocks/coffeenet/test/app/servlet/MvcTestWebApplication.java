package rocks.coffeenet.test.app.servlet;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.web.bind.annotation.RestController;

import rocks.coffeenet.test.app.AbstractTestWebApplication;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@SpringBootApplication
@RestController
public class MvcTestWebApplication extends AbstractTestWebApplication {

    @Configuration
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests().anyRequest().permitAll();
        }
    }
}
