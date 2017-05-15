# CoffeeNet Starter - Logging

This CoffeeNet-Starter preconfigure a consistent logging through all of your CoffeeNet applications.

## Usage

```java
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class LoggingClass {

  private static final Logger LOGGER = (Logger) LoggerFactory.getLogger("LoggingClass");
  ...
}
```

## Configuration
The logging starter configuration depends on the CoffeeNet profile ```coffeenet.profile```.

But it is possible to change the configuration, independent of the chosen profile ```development``` or ```integration``` at any time and remove or add specific appenders.

```yaml
coffeenet:
  application-name:
  profile: development
  logging:
    enabled: true
    console:
      enabled:
    file:
      enabled:
      file: logs/app.log
      max-history: 30
      rolling-file-name-pattern: logs/app-%d{yyyy-MM-dd}.log
      pattern: %d{yyyy-MM-dd HH:mm:ss.SSS} %5p --- [%t] %-40.40logger{39} : %m%n%wEx
    gelf:
      enabled:
      server: localhost
      port: 12201
      protocol: UDP
      environment:
      layout: %m %n
```

The configuration with their default values.

```coffeenet.application-name``` will be used to identify the application when logging into the Graylog server.

### Development

Is the ```coffeenet.profile``` set to ```development``` by default:

* the console appender will be _activated_
* the file appender will be _deactivated_
* the gelf (Graylog Extended Log Format) appender will be _deactivated_


### Integration
Is the ```coffeenet.profile``` set to ```integration``` by default:

* the console appender will be _deactivated_
* the file appender will be _activated_
* the gelf (Graylog Extended Log Format) appender will be _activated_
