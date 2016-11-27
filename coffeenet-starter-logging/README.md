# CoffeeNet Starter - Logging

Diese Abhängigkeit konfiguriert automatisch ein einheitliches Logging der Anwendung im CoffeeNet.


## Verwendung

```java
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class LoggingClass {

  private static final Logger LOGGER = (Logger) LoggerFactory.getLogger("LoggingClass");
  ...
}
```

## Konfiguration

Der Starter-Logging hängt sich standardmäßig an die Eigenschaft ```coffeenet.profile```.

Jedoch hat man sowohl bei ```development``` als auch bei ```integration``` jederzeit, unabhängig vom Profil,
die Möglichkeit jeden einzelnen Appender zu aktivieren bzw deaktivieren.

```yaml
coffeenet:
 application-name:
 profile: development
 logging:
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
```

Die Konfigurationsmöglichkeiten mit deren Standardwerte.

```coffeenet.application-name``` wird für das Logging in den Graylog verwendet, damit die Applikation identifiziert werden kann.

### Development

Ist ```coffeenet.profile``` auf ```development``` gesetzt, wird per default:

* der console appender automatisch _aktiviert_
* der file appender aotomatisch _deaktiviert_
* der gelf (Graylog Extended Log Format) appender aotomatisch _deaktiviert_


### Integration

Ist ```coffeenet.profile``` auf ```integration``` gesetzt, wird per default:

* der console appender automatisch _deaktiviert_
* der file appender aotomatisch _aktiviert_
* der gelf (Graylog Extended Log Format) appender aotomatisch _aktiviert_
