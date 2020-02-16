package rocks.coffeenet.legacy.actuate;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import org.springframework.http.HttpHeaders;

import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.is;

import static org.junit.Assert.assertThat;

import static org.mockito.Mockito.when;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@RunWith(MockitoJUnitRunner.class)
public class AuthServerHealthIndicatorTest {

    private AuthServerHealthIndicator sut;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {

        sut = new AuthServerHealthIndicator(restTemplate, "url");
    }


    @Test
    public void healthIsUp() {

        when(restTemplate.headForHeaders("url")).thenReturn(new HttpHeaders());

        Health.Builder builder = new Health.Builder();
        sut.doHealthCheck(builder);

        assertThat(builder.build().getStatus(), is(Status.UP));
    }


    @Test
    public void healthIsDownWithServerError() {

        when(restTemplate.headForHeaders("url")).thenThrow(new HttpServerErrorException(INTERNAL_SERVER_ERROR));

        Health.Builder builder = new Health.Builder();
        sut.doHealthCheck(builder);

        assertThat(builder.build().getStatus(), is(Status.DOWN));
    }
}
