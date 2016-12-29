package coffee.synyx.autoconfigure.security.config.development;

import coffee.synyx.autoconfigure.security.config.CoffeeNetSecurityProperties;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@EnableConfigurationProperties(CoffeeNetSecurityProperties.class)
public class DevelopmentCoffeeNetWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private CoffeeNetSecurityProperties securityConfigurationProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String[] freeResources = { "/fonts/**", "/css/**", "/img/**" };
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
            .roles("COFFEENET-ADMIN")
            .and()
            .withUser("user")
            .password("user");
    }


    @Autowired
    public void setSecurityConfigurationProperties(CoffeeNetSecurityProperties securityConfigurationProperties) {

        this.securityConfigurationProperties = securityConfigurationProperties;
    }
}
