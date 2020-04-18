package rocks.coffeenet.legacy.autoconfigure.security.config;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.DefaultRedirectStrategy;

import java.io.IOException;

import javax.servlet.ServletException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * @author  Tobias Schneider
 */
public class IntegrationCoffeeNetSecurityConfigurationTest {

    @Test
    public void configureCorrectDefaultLoginFailureUrl() throws IOException, ServletException {

        CoffeeNetSecurityProperties coffeeNetSecurityProperties = new CoffeeNetSecurityProperties();
        coffeeNetSecurityProperties.setDefaultLoginFailureUrl("/this-is-a-test-url");

        CoffeeNetSecurityAutoConfiguration.IntegrationCoffeeNetSecurityConfiguration sut =
            new CoffeeNetSecurityAutoConfiguration.IntegrationCoffeeNetSecurityConfiguration(
                new CoffeeNetSecurityClientProperties(), new CoffeeNetSecurityResourceProperties(),
                coffeeNetSecurityProperties);

        CoffeeNetSimpleUrlAuthenticationFailureHandler authenticationFailureHandler =
            (CoffeeNetSimpleUrlAuthenticationFailureHandler) sut.defaultAuthenticationFailureHandler();

        DefaultRedirectStrategy redirectStrategyMock = mock(DefaultRedirectStrategy.class);
        authenticationFailureHandler.setRedirectStrategy(redirectStrategyMock);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticationServiceException test = new AuthenticationServiceException("test");

        authenticationFailureHandler.onAuthenticationFailure(request, response, test);

        verify(redirectStrategyMock).sendRedirect(request, response, "/this-is-a-test-url");
    }
}
