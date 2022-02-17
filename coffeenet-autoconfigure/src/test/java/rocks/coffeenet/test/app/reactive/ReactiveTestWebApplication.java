package rocks.coffeenet.test.app.reactive;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import org.springframework.web.bind.annotation.RestController;

import rocks.coffeenet.test.app.AbstractTestWebApplication;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@SpringBootApplication
@RestController
public class ReactiveTestWebApplication extends AbstractTestWebApplication {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http.authorizeExchange()
            .anyExchange().permitAll()
            .and().build();
    }
}
