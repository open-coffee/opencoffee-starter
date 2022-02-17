package rocks.coffeenet.legacy.autoconfigure.navigation;

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
public class CoffeeNetNavigationInterceptorTest {

    private CoffeeNetNavigationInterceptor sut;

    @Mock
    private CoffeeNetNavigationService coffeeNetNavigationServiceMock;

    @Before
    public void setup() {

        sut = new CoffeeNetNavigationInterceptor(coffeeNetNavigationServiceMock);
    }


    @Test
    public void postHandleWithModelAndView() throws Exception {

        CoffeeNetNavigationInformation coffeeNetNavigationInformation = new CoffeeNetNavigationInformation(null, null,
                null, null, null);
        when(coffeeNetNavigationServiceMock.get()).thenReturn(coffeeNetNavigationInformation);

        ModelAndView modelAndView = new ModelAndView();

        sut.postHandle(null, null, null, modelAndView);

        assertThat(modelAndView.getModelMap().containsKey("coffeenet")).isEqualTo(true);

        CoffeeNetNavigationInformation coffeenet = (CoffeeNetNavigationInformation) modelAndView
                .getModelMap().get("coffeenet");
        assertThat(coffeenet).isEqualTo(coffeeNetNavigationInformation);
    }


    @Test
    public void postHandleWithoutModelAndView() throws Exception {

        sut.postHandle(null, null, null, null);

        verify(coffeeNetNavigationServiceMock, never()).get();
    }


    @Test
    public void postHandleIsRedirect() throws Exception {

        sut.postHandle(null, null, null, new ModelAndView("redirect:coffeeNetView"));

        verify(coffeeNetNavigationServiceMock, never()).get();
    }
}
