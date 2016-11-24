# Spring Boot Starter - Logging

## Beschreibung
Diese Abhängigkeit konfiguriert ein einheitliches Logging im CoffeeNet.

### Konfiguration

Der Starter-Logging hängt sich standardmäßig an die Eigenschaft ```coffeenet.profile```.

Jedoch hat man sowohl bei ```development``` als auch bei ```integration``` jederzeit, unabhängig vom Profil,
die Möglichkeit jeden einzelnen Appender zu aktivieren bzw deaktivieren.

```
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

```coffeenet.application-name``` wird für das Logging in den Graylog verwendet, damit der Host identifiziert werden kann.

##### Development

Ist ```coffeenet.profile``` auf ```development``` gesetzt, wird per default:

* der console appender automatisch _aktiviert_
* der file appender aotomatisch _deaktiviert_
* der gelf (Graylog Extended Log Format) appender aotomatisch _deaktiviert_


##### Integration

Ist ```coffeenet.profile``` auf ```integration``` gesetzt, wird per default:

* der console appender automatisch _deaktiviert_
* der file appender aotomatisch _aktiviert_
* der gelf (Graylog Extended Log Format) appender aotomatisch _aktiviert_


## Repository

        <repositories>
          <repository>
            <id>releases.nexus.synyx.de</id>
            <url>http://nexus.synyx.de/content/repositories/releases</url>
          </repository>
        </repositories>

Bitte sicherstellen, dass das synyx nexus release repository hinterlegt ist.
