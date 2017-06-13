package coffee.synyx.autoconfigure.discovery.config;

import coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;

import org.springframework.core.env.Environment;

import org.springframework.util.StringUtils;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
class CoffeeNetDiscoveryInstanceProperties extends EurekaInstanceConfigBean {

    /**
     * The hostname if it can be determined at configuration time (otherwise it will be guessed from OS primitives).
     */
    @NotBlank(message = "Please provide a hostname of your application.")
    private String hostname = "localhost";

    private CoffeeNetConfigurationProperties coffeeNetConfigurationProperties;

    CoffeeNetDiscoveryInstanceProperties(InetUtils inetUtils,
        CoffeeNetConfigurationProperties coffeeNetConfigurationProperties) {

        super(inetUtils);

        this.coffeeNetConfigurationProperties = coffeeNetConfigurationProperties;
    }

    @Override
    public String getHostname() {

        return hostname;
    }


    @Override
    public void setHostname(String hostname) {

        this.hostname = hostname;
    }


    @Override
    public void setEnvironment(Environment environment) {

        String applicationName = coffeeNetConfigurationProperties.getApplicationName();

        if (StringUtils.hasText(applicationName)) {
            this.setAppname(applicationName);
            this.setVirtualHostName(applicationName);
            this.setSecureVirtualHostName(applicationName);
        }
    }


    @Override
    public String toString() {

        return "CoffeeNetDiscoveryInstanceProperties{"
            + "hostname='" + hostname + '\''
            + ", coffeeNetConfigurationProperties=" + coffeeNetConfigurationProperties + '}';
    }
}
