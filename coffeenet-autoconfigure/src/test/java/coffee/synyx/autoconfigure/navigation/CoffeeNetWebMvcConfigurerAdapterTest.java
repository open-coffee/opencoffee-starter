package coffee.synyx.autoconfigure.navigation;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author  Tobias Schneider
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetWebMvcConfigurerAdapterTest {

    private CoffeeNetWebMvcConfigurerAdapter sut;

    @Mock
    private CoffeeNetWebInterceptor coffeeNetWebInterceptorMock;

    @Before
    public void setup() {

        sut = new CoffeeNetWebMvcConfigurerAdapter(coffeeNetWebInterceptorMock);
    }


    @Test
    public void addInterceptors() throws Exception {

        InterceptorRegistry interceptorRegistryMock = mock(InterceptorRegistry.class);
        InterceptorRegistration interceptorRegistrationMock = mock(InterceptorRegistration.class);
        when(interceptorRegistryMock.addInterceptor(coffeeNetWebInterceptorMock)).thenReturn(
            interceptorRegistrationMock);

        sut.addInterceptors(interceptorRegistryMock);

        verify(interceptorRegistryMock).addInterceptor(coffeeNetWebInterceptorMock);
        verify(interceptorRegistrationMock).addPathPatterns("/**");
    }
}
