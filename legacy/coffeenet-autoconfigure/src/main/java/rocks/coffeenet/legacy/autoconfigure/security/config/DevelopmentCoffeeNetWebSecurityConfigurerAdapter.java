package rocks.coffeenet.legacy.autoconfigure.security.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * This adapter configures the CoffeeNet development (mock) security configuration.
 *
 * <p>By default two user will be provided:</p>
 *
 * <ul>
 *   <li>admin with the password admin and the role 'COFFEENET-ADMIN'</li>
 *   <li>user with the password user without a role</li>
 * </ul>
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@EnableConfigurationProperties(CoffeeNetSecurityProperties.class)
public class DevelopmentCoffeeNetWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private CoffeeNetSecurityProperties securityConfigurationProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String[] freeResources = { "/fonts/**", "/css/**", "/img/**", "/health", "/info" };
        http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers(freeResources)
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin();

        if (securityConfigurationProperties.getDefaultLoginSuccessUrl() != null) {
            http.formLogin().defaultSuccessUrl(securityConfigurationProperties.getDefaultLoginSuccessUrl(), true);
        }
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("admin")
            .roles("COFFEENET-ADMIN", "USER")
            .and()
            .withUser("user")
            .password("user")
            .roles("USER");
    }


    @Autowired
    public void setSecurityConfigurationProperties(CoffeeNetSecurityProperties securityConfigurationProperties) {

        this.securityConfigurationProperties = securityConfigurationProperties;
    }
}
