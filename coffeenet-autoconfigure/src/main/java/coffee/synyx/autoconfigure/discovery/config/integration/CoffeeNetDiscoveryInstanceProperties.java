package coffee.synyx.autoconfigure.discovery.config.integration;

import coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;

import org.springframework.util.StringUtils;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
class CoffeeNetDiscoveryInstanceProperties extends EurekaInstanceConfigBean {

    /**
     * The default server port if nothing else is specified.
     */
    private static final int DEFAULT_SERVER_PORT = 8080;

    /**
     * The hostname if it can be determined at configuration time (otherwise it will be guessed from OS primitives).
     */
    @NotBlank
    private String hostname = "localhost";

    private CoffeeNetConfigurationProperties coffeeNetConfigurationProperties;

    CoffeeNetDiscoveryInstanceProperties(InetUtils inetUtils, ServerProperties serverProperties,
        CoffeeNetConfigurationProperties coffeeNetConfigurationProperties) {

        super(inetUtils);

        this.coffeeNetConfigurationProperties = coffeeNetConfigurationProperties;

        Integer serverPort = serverProperties.getPort();

        if (serverPort == null) {
            serverPort = DEFAULT_SERVER_PORT;
        }

        setNonSecurePort(serverPort);
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
    public void afterPropertiesSet() {

        String allowedAuthorities = coffeeNetConfigurationProperties.getAllowedAuthorities();

        if (allowedAuthorities != null) {
            getMetadataMap().put("allowedAuthorities", allowedAuthorities);
        }

        String applicationName = coffeeNetConfigurationProperties.getApplicationName();

        if (StringUtils.hasText(applicationName)) {
            this.setAppname(applicationName);
            this.setVirtualHostName(applicationName);
            this.setSecureVirtualHostName(applicationName);
        }
    }
}
