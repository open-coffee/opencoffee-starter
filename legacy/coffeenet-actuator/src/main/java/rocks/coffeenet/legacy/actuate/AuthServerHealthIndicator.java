package rocks.coffeenet.legacy.actuate;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;


public class AuthServerHealthIndicator extends AbstractHealthIndicator {

    private final RestTemplate restTemplate;
    private final String checkUrl;

    public AuthServerHealthIndicator(RestTemplate restTemplate, String checkUrl) {

        this.restTemplate = restTemplate;
        this.checkUrl = checkUrl;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {

        try {
            restTemplate.headForHeaders(checkUrl);
            builder.up();
        } catch (HttpServerErrorException | UnknownHttpStatusCodeException | ResourceAccessException e) {
            builder.down(e);
        }
    }
}
