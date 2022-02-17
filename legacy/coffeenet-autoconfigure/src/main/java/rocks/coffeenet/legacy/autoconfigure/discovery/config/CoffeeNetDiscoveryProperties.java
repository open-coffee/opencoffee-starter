package rocks.coffeenet.legacy.autoconfigure.discovery.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Discovery properties to configure the behaviour of the discovery starter.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.12.0
 */
@ConfigurationProperties("coffeenet.discovery")
public class CoffeeNetDiscoveryProperties {

    private Boolean enabled = true;

    public Boolean isEnabled() {

        return enabled;
    }


    public void setEnabled(Boolean enabled) {

        this.enabled = enabled;
    }


    @Override
    public String toString() {

        return "CoffeeNetDiscoveryProperties{"
            + "enabled=" + enabled + '}';
    }
}
