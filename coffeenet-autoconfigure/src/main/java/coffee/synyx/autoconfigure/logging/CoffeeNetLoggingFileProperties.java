package coffee.synyx.autoconfigure.logging;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Logging properties to configure the behaviour of the file logging.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.9.0
 */
@ConfigurationProperties("coffeenet.logging.file")
public class CoffeeNetLoggingFileProperties {

    private Boolean enabled;
    private String file = "logs/app.txt";
    private String rollingFNP = file + "%i";
    private String pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} %5p --- [%t] %-40.40logger{39} : %m%n%wEx";
    private String maxFileSize = "10MB";

    public Boolean isEnabled() {

        return enabled;
    }


    public void setEnabled(Boolean enabled) {

        this.enabled = enabled;
    }


    public String getFile() {

        return file;
    }


    public void setFile(String file) {

        this.file = file;
    }


    public String getMaxFileSize() {

        return maxFileSize;
    }


    public void setMaxFileSize(String maxFileSize) {

        this.maxFileSize = maxFileSize;
    }


    public String getPattern() {

        return pattern;
    }


    public void setPattern(String pattern) {

        this.pattern = pattern;
    }


    public String getRollingFNP() {

        return rollingFNP;
    }


    public void setRollingFNP(String rollingFNP) {

        this.rollingFNP = rollingFNP;
    }
}
