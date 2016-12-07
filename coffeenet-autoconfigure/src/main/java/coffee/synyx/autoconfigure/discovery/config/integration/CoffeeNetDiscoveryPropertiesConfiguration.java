package coffee.synyx.autoconfigure.discovery.config.integration;

import coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.INTEGRATION;


/**
 * Provides {@link EurekaInstanceConfigBean} and {@link EurekaClientConfigBean} to be configured by properties with
 * coffeenet.discovery prefix.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
@Configuration
@ConditionalOnClass(DiscoveryClient.class)
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = INTEGRATION)
@AutoConfigureBefore(EurekaClientAutoConfiguration.class)
@EnableConfigurationProperties({ CoffeeNetConfigurationProperties.class, CoffeeNetDiscoveryProperties.class })
public class CoffeeNetDiscoveryPropertiesConfiguration {

    private CoffeeNetConfigurationProperties coffeeNetConfigurationProperties;

    @Autowired
    public CoffeeNetDiscoveryPropertiesConfiguration(
        CoffeeNetConfigurationProperties coffeeNetConfigurationProperties) {

        this.coffeeNetConfigurationProperties = coffeeNetConfigurationProperties;
    }

    @Bean
    @ConfigurationProperties(prefix = "coffeenet.discovery.instance")
    public CoffeeNetDiscoveryInstanceProperties eurekaInstanceConfigBean(InetUtils inetUtils,
        ServerProperties serverProperties) {

        return new CoffeeNetDiscoveryInstanceProperties(inetUtils, serverProperties, coffeeNetConfigurationProperties);
    }


    @Bean
    @ConfigurationProperties(prefix = "coffeenet.discovery.client")
    public EurekaClientConfigBean eurekaClientConfigBean() {

        return new EurekaClientConfigBean();
    }
}
