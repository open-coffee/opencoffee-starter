package coffee.synyx.autoconfigure.security.service;

import org.junit.After;
import org.junit.Test;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.HashSet;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import static org.junit.Assert.assertThat;


/**
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class IntegrationCoffeeNetCurrentUserServiceTest {

    private IntegrationCoffeeNetCurrentUserService sut = new IntegrationCoffeeNetCurrentUserService();

    @After
    public void tearDown() throws Exception {

        SecurityContextHolder.clearContext();
    }


    @Test
    public void get() {

        CoffeeNetUserDetails principal = new HumanCoffeeNetUser("test", "test@synyx.de", new HashSet<>());

        SecurityContextImpl context = new SecurityContextImpl();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(principal, null));
        SecurityContextHolder.setContext(context);

        CoffeeNetUserDetails result = sut.get();

        assertThat(result, is(principal));
    }


    @Test
    public void getWithoutAuthenticationInContext() {

        CoffeeNetUserDetails result = sut.get();

        assertThat(result, nullValue());
    }


    @Test
    public void getWithoutPrincipal() {

        SecurityContextImpl context = new SecurityContextImpl();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(null, null));
        SecurityContextHolder.setContext(context);

        CoffeeNetUserDetails result = sut.get();

        assertThat(result, nullValue());
    }


    @Test
    public void getWithoutCoffeenetPrincipal() {

        SecurityContextImpl context = new SecurityContextImpl();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(new Object(), null));
        SecurityContextHolder.setContext(context);

        CoffeeNetUserDetails result = sut.get();

        assertThat(result, nullValue());
    }
}
