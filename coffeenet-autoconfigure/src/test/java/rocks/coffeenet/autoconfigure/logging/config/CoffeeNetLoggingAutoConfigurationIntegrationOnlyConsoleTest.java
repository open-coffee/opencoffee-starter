package rocks.coffeenet.autoconfigure.logging.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.ConsoleAppender;

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

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import static org.slf4j.Logger.ROOT_LOGGER_NAME;


/**
 * @author  Tobias Schneider
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoffeeNetLoggingAutoConfiguration.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:logging/application-test-logging-integration-only-console.properties")
@DirtiesContext
public class CoffeeNetLoggingAutoConfigurationIntegrationOnlyConsoleTest {

    @Autowired
    private CoffeeNetLoggingAutoConfiguration sut;

    @Test
    public void integration() {

        final Logger logger = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);

        assertThat(logger.getAppender("CONSOLE"), is(instanceOf(ConsoleAppender.class)));
        assertThat(logger.getAppender("COFFEENET-FILE"), is(nullValue()));
        assertThat(logger.getAppender("COFFEENET-GELF"), is(nullValue()));
    }
}
