package coffee.synyx.autoconfigure.logging.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.RollingPolicy;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

import de.appelgriepsch.logback.GelfAppender;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import static org.slf4j.Logger.ROOT_LOGGER_NAME;


/**
 * This test will test the configuration of all default appenders used by the coffeenet.
 *
 * @author  Tobias Schneider
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoffeeNetLoggingConfiguration.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:logging/application-test-logging-all-appenders.properties")
@DirtiesContext
public class CoffeeNetLoggingConfigurationTest {

    @Autowired
    private CoffeeNetLoggingConfiguration sut;

    @Test
    public void integration() {

        final Logger logger = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);

        assertThat(logger.getAppender("CONSOLE"), is(instanceOf(ConsoleAppender.class)));

        Appender<ILoggingEvent> fileAppender = logger.getAppender("COFFEENET-FILE");
        assertThat(fileAppender, is(instanceOf(RollingFileAppender.class)));

        // Check File Appender
        RollingFileAppender rollingFileAppender = (RollingFileAppender) fileAppender;
        assertThat(rollingFileAppender.getFile(), is("logs/app.log"));
        assertThat(rollingFileAppender.getName(), is("COFFEENET-FILE"));

        RollingPolicy rollingPolicy = rollingFileAppender.getRollingPolicy();
        assertThat(rollingPolicy, is(instanceOf(TimeBasedRollingPolicy.class)));

        TimeBasedRollingPolicy timeBasedRollingPolicy = (TimeBasedRollingPolicy) rollingFileAppender.getRollingPolicy();
        assertThat(timeBasedRollingPolicy.getFileNamePattern(), is("logs/app-%d{yyyy-MM-dd}.log"));

        PatternLayoutEncoder encoder = (PatternLayoutEncoder) rollingFileAppender.getEncoder();
        assertThat(encoder.getPattern(), is("%d{yyyy-MM-dd HH:mm:ss.SSS} %5p --- [%t] %-40.40logger{39} : %m%n%wEx"));

        // Check Gelf Appender
        Appender<ILoggingEvent> gelfAppender = logger.getAppender("COFFEENET-GELF");
        assertThat(gelfAppender, is(instanceOf(GelfAppender.class)));

        GelfAppender gelfAppender1 = (GelfAppender) gelfAppender;
        assertThat(gelfAppender1.getName(), is("COFFEENET-GELF"));
        assertThat(gelfAppender1.getServer(), is("localServer"));
        assertThat(gelfAppender1.getProtocol(), is("TCP"));
        assertThat(gelfAppender1.getPort(), is(1337));
        assertThat(((PatternLayout) gelfAppender1.getLayout()).getPattern(), is("%m"));
        assertThat(gelfAppender1.getAdditionalFields(), hasEntry("environment", "test"));
        assertThat(gelfAppender1.getAdditionalFields(), hasEntry("application", "CoffeeNetApplication"));
    }
}
