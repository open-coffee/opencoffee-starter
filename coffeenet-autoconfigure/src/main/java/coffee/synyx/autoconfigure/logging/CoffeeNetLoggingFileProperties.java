package coffee.synyx.autoconfigure.logging;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * Logging properties to configure the behaviour of the file logging.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.9.0
 */
@Validated
@ConfigurationProperties("coffeenet.logging.file")
public class CoffeeNetLoggingFileProperties {

    private static final int DEFAULT_HISTORY_30 = 30;

    private Boolean enabled;

    @NotBlank(message = "Please provide a file where your logs will be written e.g. logs/app.log")
    private String file = "logs/app.log";

    @NotBlank(message = "Please provide a file name pattern with a date pattern e.g. logs/app-%d{yyyy-MM-dd}.log")
    private String fileNamePattern = "logs/app-%d{yyyy-MM-dd}.log";

    @NotBlank(message = "Please provide a log patter for your logs.")
    private String pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} %5p --- [%t] %-40.40logger{39} : %m%n%wEx";

    @Min(value = 1, message = "Only positive integers are allowed for the maximum amount of history files.")
    @NotNull(message = "Please provide a maximum amount of history files you want to keep.")
    private int maxHistory = DEFAULT_HISTORY_30;

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
