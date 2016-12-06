package coffee.synyx.autoconfigure.logging;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Logging properties to configure the behaviour of the logging.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.12.0
 */
@ConfigurationProperties("coffeenet.logging")
public class CoffeeNetLoggingProperties {

    private Boolean enabled = true;

    public Boolean isEnabled() {

        return enabled;
    }


    public void setEnabled(Boolean enabled) {

        this.enabled = enabled;
    }
}
