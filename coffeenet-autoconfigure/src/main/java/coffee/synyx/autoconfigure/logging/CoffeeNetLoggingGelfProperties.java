package coffee.synyx.autoconfigure.logging;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Logging properties to configure the behaviour of the gelf logging.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.9.0
 */
@ConfigurationProperties("coffeenet.logging.gelf")
public class CoffeeNetLoggingGelfProperties {

    private static final int DEFAULT_PORT = 12201;

    private Boolean enabled;
    private String server = "localhost";
    private int port = DEFAULT_PORT;
    private String protocol = "UDP";

    public Boolean isEnabled() {

        return enabled;
    }


    public void setEnabled(Boolean enabled) {

        this.enabled = enabled;
    }


    public String getServer() {

        return server;
    }


    public void setServer(String server) {

        this.server = server;
    }


    public int getPort() {

        return port;
    }


    public void setPort(int port) {

        this.port = port;
    }


    public String getProtocol() {

        return protocol;
    }


    public void setProtocol(String protocol) {

        this.protocol = protocol;
    }
}
