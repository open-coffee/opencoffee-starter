package rocks.coffeenet.legacy.autoconfigure.discovery.config;

import com.netflix.discovery.EurekaClientConfig;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertyResolver;

import org.springframework.util.StringUtils;

import rocks.coffeenet.legacy.autoconfigure.CoffeeNetConfigurationProperties;
import rocks.coffeenet.legacy.autoconfigure.discovery.service.CoffeeNetAppService;
import rocks.coffeenet.legacy.autoconfigure.discovery.service.DevelopmentCoffeeNetAppService;
import rocks.coffeenet.legacy.autoconfigure.discovery.service.IntegrationCoffeeNetAppService;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import static org.springframework.cloud.commons.util.IdUtils.getDefaultInstanceId;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;


/**
 * Discovery Configuration.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@AutoConfigureBefore(EurekaClientAutoConfiguration.class)
@ConditionalOnProperty(prefix = "coffeenet.discovery", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CoffeeNetDiscoveryAutoConfiguration {

    @Configuration
    @ConditionalOnClass(EurekaClientConfig.class)
    @ConditionalOnProperty(
        prefix = "coffeenet", name = "profile", havingValue = CoffeeNetConfigurationProperties.DEVELOPMENT,
        matchIfMissing = true
    )
    static class DevelopmentCoffeeNetServiceDiscoveryConfiguration {

        @Bean
        @ConditionalOnMissingBean(CoffeeNetAppService.class)
        CoffeeNetAppService coffeeNetAppService() {

            return new DevelopmentCoffeeNetAppService();
        }
    }

    @Configuration
    @EnableDiscoveryClient
    @ConditionalOnClass(EurekaClientConfig.class)
    @ConditionalOnProperty(
        prefix = "coffeenet", name = "profile", havingValue = CoffeeNetConfigurationProperties.INTEGRATION
    )
    static class IntegrationCoffeeNetServiceDiscoveryConfiguration {

        private final DiscoveryClient discoveryClient;

        @Autowired
        public IntegrationCoffeeNetServiceDiscoveryConfiguration(DiscoveryClient discoveryClient) {

            this.discoveryClient = discoveryClient;
        }

        @Bean
        @ConditionalOnMissingBean(CoffeeNetAppService.class)
        CoffeeNetAppService coffeeNetAppService() {

            return new IntegrationCoffeeNetAppService(discoveryClient);
        }
    }

    @Configuration
    @ConditionalOnClass(EurekaDiscoveryClient.class)
    @ConditionalOnProperty(
        prefix = "coffeenet", name = "profile", havingValue = CoffeeNetConfigurationProperties.INTEGRATION
    )
    @EnableConfigurationProperties({ CoffeeNetConfigurationProperties.class, CoffeeNetDiscoveryProperties.class })
    static class CoffeeNetDiscoveryPropertiesConfiguration {

        private final CoffeeNetConfigurationProperties coffeeNetConfigurationProperties;

        @Autowired
        CoffeeNetDiscoveryPropertiesConfiguration(CoffeeNetConfigurationProperties coffeeNetConfigurationProperties) {

            this.coffeeNetConfigurationProperties = coffeeNetConfigurationProperties;
        }

        @Bean
        CoffeeNetDiscoveryInstanceProperties eurekaInstanceConfigBean(InetUtils inetUtils, ConfigurableEnvironment env)
            throws MalformedURLException {

            RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(env);

            PropertyResolver coffeeNetPropertyResolver = new RelaxedPropertyResolver(env,
                    "coffeenet.discovery.instance.");
            String hostname = coffeeNetPropertyResolver.getProperty("hostname");

            boolean preferIpAddress = parseBoolean(coffeeNetPropertyResolver.getProperty("preferIpAddress"));
            int nonSecurePort = parseInt(resolver.getProperty("server.port", resolver.getProperty("port", "8080")));
            int managementPort = parseInt(resolver.getProperty("management.port", String.valueOf(nonSecurePort)));
            String managementContextPath = resolver.getProperty("management.contextPath",
                    resolver.getProperty("server.contextPath", "/"));

            CoffeeNetDiscoveryInstanceProperties instance = new CoffeeNetDiscoveryInstanceProperties(inetUtils,
                    coffeeNetConfigurationProperties);
            instance.setNonSecurePort(nonSecurePort);
            instance.setInstanceId(getDefaultInstanceId(resolver));
            instance.setPreferIpAddress(preferIpAddress);

            if (managementPort != nonSecurePort && managementPort != 0) {
                if (StringUtils.hasText(hostname)) {
                    instance.setHostname(hostname);
                }

                String statusPageUrlPath = coffeeNetPropertyResolver.getProperty("statusPageUrlPath");
                String healthCheckUrlPath = coffeeNetPropertyResolver.getProperty("healthCheckUrlPath");

                if (!managementContextPath.endsWith(File.separator)) {
                    managementContextPath = managementContextPath + File.separator;
                }

                if (StringUtils.hasText(statusPageUrlPath)) {
                    instance.setStatusPageUrlPath(statusPageUrlPath);
                }

                if (StringUtils.hasText(healthCheckUrlPath)) {
                    instance.setHealthCheckUrlPath(healthCheckUrlPath);
                }

                String scheme = instance.getSecurePortEnabled() ? "https" : "http";
                URL base = new URL(scheme, instance.getHostname(), managementPort, managementContextPath);
                instance.setStatusPageUrl(
                    new URL(base, StringUtils.trimLeadingCharacter(instance.getStatusPageUrlPath(), '/')).toString());
                instance.setHealthCheckUrl(
                    new URL(base, StringUtils.trimLeadingCharacter(instance.getHealthCheckUrlPath(), '/')).toString());
            }

            return instance;
        }


        @Bean
        CoffeeNetDiscoveryClientProperties eurekaClientConfigBean() {

            return new CoffeeNetDiscoveryClientProperties();
        }
    }
}
