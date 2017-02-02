package coffee.synyx.autoconfigure.logging;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.Min;


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

    @NotBlank(message = "Please provide the server address of your graylog server.")
    private String server = "localhost";

    private String environment;

    @Min(value = 1, message = "Please provide the port of your graylog server.")
    private int port = DEFAULT_PORT;

    @NotBlank(message = "Please provide the protocol of the communication with your graylog server.")
    private String protocol = "UDP";

    @NotBlank(message = "Please provide the layout of the message which will be send to the graylog server.")
    private String layout = "%m %n";

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


    public String getEnvironment() {

        return environment;
    }


    public void setEnvironment(String environment) {

        this.environment = environment;
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


    public String getLayout() {

        return layout;
    }


    public void setLayout(String layout) {

        this.layout = layout;
    }
}
