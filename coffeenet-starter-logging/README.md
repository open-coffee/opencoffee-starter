# CoffeeNet Starter - Logging

This CoffeeNet starter configures a consistent logging format
and behaviour through all of your CoffeeNet applications.


## Getting started

This is a module in the starter set, so you first need to declare your project
as a child of the starter `parent` by editing the `pom.xml` file.

```xml
<parent>
    <groupId>coffee.synyx</groupId>
    <artifactId>coffeenet-starter-parent</artifactId>
    <version>${parent.version}</version>
    <relativePath />
</parent>
```

and adding the repository to receive the dependencies

```xml
<repositories>
  <repository>
    <id>releases.public.nexus.synyx.de</id>
    <url>http://nexus.synyx.de/content/repositories/public-releases</url>
  </repository>
</repositories>
```

Now you can enable logging in your project, by first adding the dependency:

```xml
<dependency>
    <groupId>coffee.synyx</groupId>
    <artifactId>starter-logging</artifactId>
</dependency>
```

In order to get everything up and running there are some requirements that
your project must fulfill.


## Usage

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;

public class LoggingClass {

  private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
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
      file-name-pattern: logs/app-%d{yyyy-MM-dd}.log
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