package coffee.synyx.starter.web;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author  Tobias Schneider
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetWebInterceptorTest {

    private CoffeeNetWebInterceptor sut;

    @Mock
    private CoffeeNetWebService coffeeNetWebServiceMock;

    @Before
    public void setup() {

        sut = new CoffeeNetWebInterceptor(coffeeNetWebServiceMock);
    }


    @Test
    public void postHandleWithModelAndView() throws Exception {

        CoffeeNetWeb coffeeNetWeb = new CoffeeNetWeb(null, null, null, null);
        when(coffeeNetWebServiceMock.get()).thenReturn(coffeeNetWeb);

        ModelAndView modelAndView = new ModelAndView();

        sut.postHandle(null, null, null, modelAndView);

        assertThat(modelAndView.getModelMap().containsKey("coffeenet")).isEqualTo(true);

        CoffeeNetWeb coffeenet = (CoffeeNetWeb) modelAndView.getModelMap().get("coffeenet");
        assertThat(coffeenet).isEqualTo(coffeeNetWeb);
    }


    @Test
    public void postHandleWithoutModelAndView() throws Exception {

        sut.postHandle(null, null, null, null);

        verify(coffeeNetWebServiceMock, never()).get();
    }


    @Test
    public void postHandleIsRedirect() throws Exception {

        sut.postHandle(null, null, null, new ModelAndView("redirect:coffeeNetView"));

        verify(coffeeNetWebServiceMock, never()).get();
    }
}
