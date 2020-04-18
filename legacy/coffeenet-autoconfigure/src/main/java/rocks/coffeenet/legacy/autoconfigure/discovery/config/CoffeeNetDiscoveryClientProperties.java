package rocks.coffeenet.legacy.autoconfigure.discovery.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;

import org.springframework.validation.annotation.Validated;


/**
 * @author  Tobias Schneider
 */
@Validated
@ConfigurationProperties(prefix = "coffeenet.discovery.client")
public class CoffeeNetDiscoveryClientProperties extends EurekaClientConfigBean {
}
