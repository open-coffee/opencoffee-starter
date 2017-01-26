package coffee.synyx.autoconfigure.discovery.config;

import coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.discovery.service.DevelopmentCoffeeNetAppService;
import coffee.synyx.autoconfigure.discovery.service.IntegrationEurekaCoffeeNetAppService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.DEVELOPMENT;
import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.INTEGRATION;


/**
 * Discovery Configuration.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@ConditionalOnProperty(prefix = "coffeenet.discovery", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CoffeeNetDiscoveryAutoConfiguration {

    @Configuration
    @ConditionalOnClass(com.netflix.discovery.DiscoveryClient.class)
    @ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = DEVELOPMENT, matchIfMissing = true)
    public class DevelopmentCoffeeNetServiceDiscoveryConfiguration {

        @Bean
        @ConditionalOnMissingBean(CoffeeNetAppService.class)
        public CoffeeNetAppService coffeeNetAppService() {

            return new DevelopmentCoffeeNetAppService();
        }
    }

    @Configuration
    @EnableEurekaClient
    @ConditionalOnClass(DiscoveryClient.class)
    @ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = INTEGRATION)
    public class IntegrationCoffeeNetServiceDiscoveryConfiguration {

        private final DiscoveryClient discoveryClient;

        @Autowired
        public IntegrationCoffeeNetServiceDiscoveryConfiguration(DiscoveryClient discoveryClient) {

            this.discoveryClient = discoveryClient;
        }

        @Bean
        @ConditionalOnMissingBean(CoffeeNetAppService.class)
        public CoffeeNetAppService coffeeNetAppService() {

            return new IntegrationEurekaCoffeeNetAppService(discoveryClient);
        }
    }

    @Configuration
    @ConditionalOnClass(DiscoveryClient.class)
    @ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = INTEGRATION)
    @AutoConfigureBefore(EurekaClientAutoConfiguration.class)
    @EnableConfigurationProperties({ CoffeeNetConfigurationProperties.class, CoffeeNetDiscoveryProperties.class })
    protected class CoffeeNetDiscoveryPropertiesConfiguration {

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

            return new CoffeeNetDiscoveryInstanceProperties(inetUtils, serverProperties,
                    coffeeNetConfigurationProperties);
        }


        @Bean
        @ConfigurationProperties(prefix = "coffeenet.discovery.client")
        public EurekaClientConfigBean eurekaClientConfigBean() {

            return new EurekaClientConfigBean();
        }
    }
}
