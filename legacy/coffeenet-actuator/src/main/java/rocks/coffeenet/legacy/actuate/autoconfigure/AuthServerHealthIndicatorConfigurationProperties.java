package rocks.coffeenet.legacy.actuate.autoconfigure;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties("coffeenet.monitoring.security")
public class AuthServerHealthIndicatorConfigurationProperties {

    @URL(message = "Please provide a valid url to your oauth health endpoint")
    @NotBlank(message = "Please provide the user endpoint of the oauth server usually ending with /health")
    private String healthUri = "http://localhost:9999/health";

    public String getHealthUri() {

        return healthUri;
    }


    public void setHealthUri(String healthUri) {

        this.healthUri = healthUri;
    }
}
