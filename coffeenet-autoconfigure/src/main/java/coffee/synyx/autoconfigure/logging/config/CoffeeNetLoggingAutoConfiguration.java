package coffee.synyx.autoconfigure.logging.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

import coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties;
import coffee.synyx.autoconfigure.logging.CoffeeNetLoggingConsoleProperties;
import coffee.synyx.autoconfigure.logging.CoffeeNetLoggingFileProperties;
import coffee.synyx.autoconfigure.logging.CoffeeNetLoggingGelfProperties;

import de.appelgriepsch.logback.GelfAppender;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.INTEGRATION;

import static org.slf4j.Logger.ROOT_LOGGER_NAME;


/**
 * Default logging configuration for the {@code CoffeeNet}.
 *
 * <ol type="I">
 * <li>Integration
 *
 * <ul>
 * <li>File Appender</li>
 * <li>Gelf Appender</li>
 * </ul>
 * </li>
 * <li>Development
 *
 * <ul>
 * <li>Console Appender</li>
 * </ul>
 * </li>
 * </ol>
 *
 * <p>If you want to activate a {@code Appender} independent from the default configuration you can just enable the
 * appender via {@code coffeenet.logging.gelf.enabled = true} e.g.</p>
 *
 * <p>Analog with the other appenders (console,file,gelf)</p>
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.9.0
 */
@Configuration
@ConditionalOnClass({ LoggerContext.class, GelfAppender.class })
@EnableConfigurationProperties(
    {
        CoffeeNetLoggingFileProperties.class, CoffeeNetLoggingGelfProperties.class,
        CoffeeNetConfigurationProperties.class, CoffeeNetLoggingConsoleProperties.class
    }
)
@ConditionalOnProperty(prefix = "coffeenet.logging", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CoffeeNetLoggingAutoConfiguration {

    private static final String CONSOLE = "CONSOLE";
    private static final String COFFEENET_FILE_APPENDER = "COFFEENET-FILE";
    private static final String COFFEENET_GELF = "COFFEENET-GELF";

    private static final LoggerContext LOGGER_CONTEXT = (LoggerContext) LoggerFactory.getILoggerFactory();
    private static final Logger LOGGER = LOGGER_CONTEXT.getLogger(ROOT_LOGGER_NAME);

    private final CoffeeNetLoggingConsoleProperties coffeeNetLoggingConsoleProperties;
    private final CoffeeNetLoggingFileProperties coffeeNetLoggingFileProperties;
    private final CoffeeNetLoggingGelfProperties coffeeNetLoggingGelfProperties;
    private final CoffeeNetConfigurationProperties coffeeNetConfigurationProperties;

    @Autowired
    public CoffeeNetLoggingAutoConfiguration(CoffeeNetLoggingFileProperties coffeeNetLoggingFileProperties,
        CoffeeNetLoggingGelfProperties coffeeNetLoggingGelfProperties,
        CoffeeNetConfigurationProperties coffeeNetConfigurationProperties,
        CoffeeNetLoggingConsoleProperties coffeeNetLoggingConsoleProperties) {

        this.coffeeNetLoggingFileProperties = coffeeNetLoggingFileProperties;
        this.coffeeNetLoggingGelfProperties = coffeeNetLoggingGelfProperties;
        this.coffeeNetConfigurationProperties = coffeeNetConfigurationProperties;
        this.coffeeNetLoggingConsoleProperties = coffeeNetLoggingConsoleProperties;
    }

    @PostConstruct
    public void configure() {

        if (disableConsoleAppender()) {
            LOGGER.detachAppender(CONSOLE);
        }

        if (enableFileAppender()) {
            LOGGER.addAppender(rollingFileAppender(LOGGER_CONTEXT));
        }

        if (enableGelfAppender()) {
            LOGGER.addAppender(gelfAppender(LOGGER_CONTEXT));
        }
    }


    private boolean disableConsoleAppender() {

        boolean disabled;

        if (coffeeNetLoggingConsoleProperties.isEnabled() == null) {
            disabled = INTEGRATION.equalsIgnoreCase(coffeeNetConfigurationProperties.getProfile().name());
        } else {
            disabled = !coffeeNetLoggingConsoleProperties.isEnabled();
        }

        return disabled;
    }


    private boolean enableGelfAppender() {

        boolean enabled;

        if (coffeeNetLoggingGelfProperties.isEnabled() == null) {
            enabled = INTEGRATION.equalsIgnoreCase(coffeeNetConfigurationProperties.getProfile().name());
        } else {
            enabled = coffeeNetLoggingGelfProperties.isEnabled();
        }

        return enabled;
    }


    private boolean enableFileAppender() {

        boolean enabled;

        if (coffeeNetLoggingFileProperties.isEnabled() == null) {
            enabled = INTEGRATION.equalsIgnoreCase(coffeeNetConfigurationProperties.getProfile().name());
        } else {
            enabled = coffeeNetLoggingFileProperties.isEnabled();
        }

        return enabled;
    }


    private GelfAppender gelfAppender(LoggerContext loggerContext) {

        GelfAppender gelfAppender = new GelfAppender();
        gelfAppender.setContext(loggerContext);
        gelfAppender.setName(COFFEENET_GELF);
        gelfAppender.setServer(coffeeNetLoggingGelfProperties.getServer());
        gelfAppender.setProtocol(coffeeNetLoggingGelfProperties.getProtocol());
        gelfAppender.setPort(coffeeNetLoggingGelfProperties.getPort());
        gelfAppender.addAdditionalField("application", coffeeNetConfigurationProperties.getApplicationName());
        gelfAppender.addAdditionalField("environment", coffeeNetLoggingGelfProperties.getEnvironment());

        PatternLayout patternLayout = new PatternLayout();
        patternLayout.setContext(loggerContext);
        patternLayout.setPattern(coffeeNetLoggingGelfProperties.getLayout());
        patternLayout.start();

        gelfAppender.setLayout(patternLayout);
        gelfAppender.start();

        return gelfAppender;
    }


    private RollingFileAppender<ILoggingEvent> rollingFileAppender(LoggerContext loggerContext) {

        RollingFileAppender<ILoggingEvent> rfAppender = new RollingFileAppender<>();
        rfAppender.setName(COFFEENET_FILE_APPENDER);
        rfAppender.setContext(loggerContext);
        rfAppender.setFile(coffeeNetLoggingFileProperties.getFile());

        // TimeBasedRollingPolicy
        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy();
        rollingPolicy.setContext(loggerContext);
        rollingPolicy.setParent(rfAppender);
        rollingPolicy.setFileNamePattern(coffeeNetLoggingFileProperties.getFileNamePattern());
        rollingPolicy.setMaxHistory(coffeeNetLoggingFileProperties.getMaxHistory());
        rollingPolicy.start();
        rfAppender.setRollingPolicy(rollingPolicy);

        // PatternLayoutEncoder
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern(coffeeNetLoggingFileProperties.getPattern());
        encoder.start();
        rfAppender.setEncoder(encoder);

        rfAppender.start();

        return rfAppender;
    }
}
