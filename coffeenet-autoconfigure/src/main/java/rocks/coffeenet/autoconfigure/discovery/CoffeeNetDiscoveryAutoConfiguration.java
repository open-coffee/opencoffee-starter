package rocks.coffeenet.autoconfigure.discovery;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rocks.coffeenet.platform.discovery.DiscoveryClientCoffeeNetApplications;
import rocks.coffeenet.platform.domain.app.CoffeeNetApplications;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@Configuration
@ConditionalOnClass(DiscoveryClientCoffeeNetApplications.class)
@ConditionalOnBean(DiscoveryClient.class)
@AutoConfigureAfter(CommonsClientAutoConfiguration.class)
public class CoffeeNetDiscoveryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    CoffeeNetApplications discoveryCoffeeNetApplications(DiscoveryClient discoveryClient) {

        return new DiscoveryClientCoffeeNetApplications(discoveryClient);
    }
}
