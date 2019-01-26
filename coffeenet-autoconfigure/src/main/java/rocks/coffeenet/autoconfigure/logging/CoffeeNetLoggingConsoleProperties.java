package rocks.coffeenet.autoconfigure.logging;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Logging properties to configure the behaviour of the console logging.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.9.0
 */
@ConfigurationProperties("coffeenet.logging.console")
public class CoffeeNetLoggingConsoleProperties {

    private Boolean enabled;

    public Boolean isEnabled() {

        return enabled;
    }


    public void setEnabled(Boolean enabled) {

        this.enabled = enabled;
    }


    @Override
    public String toString() {

        return "CoffeeNetLoggingConsoleProperties{"
            + "enabled=" + enabled + '}';
    }
}
