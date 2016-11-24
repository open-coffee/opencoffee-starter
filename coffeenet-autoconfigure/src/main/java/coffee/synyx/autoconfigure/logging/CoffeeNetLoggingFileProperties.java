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
    private String file = "logs/app.log";
    private String fileNamePattern = "logs/app-%d{yyyy-MM-dd}.log";
    private String pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} %5p --- [%t] %-40.40logger{39} : %m%n%wEx";
    private int maxHistory = 30;

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


    public int getMaxHistory() {

        return maxHistory;
    }


    public void setMaxHistory(int maxHistory) {

        this.maxHistory = maxHistory;
    }


    public String getPattern() {

        return pattern;
    }


    public void setPattern(String pattern) {

        this.pattern = pattern;
    }


    public String getFileNamePattern() {

        return fileNamePattern;
    }


    public void setFileNamePattern(String rollingFNP) {

        this.fileNamePattern = rollingFNP;
    }
}
