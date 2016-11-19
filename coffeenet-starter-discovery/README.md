# Spring Boot Starter - Discovery

## Beschreibung
Diese Abhängigkeit startet automatisch die konfiguration der 'Servicediscovery' einer Spring-Boot Anwendung.

## Verbindungsinformationen

Die Verbindungsinformationen, sodass eurer Service sich bei der Service Discovery melden und Informationen über andere
Services im System erhält müsst ihr lediglich in der application.[properties/yml] folgende Einstellungen vornehmen.

### Lokale Entwicklung (Mock)

Für die _gemockte lokale Entwicklung_ reicht es folgende properties zu verwenden:

         coffeenet:
           profile: development

### Lokale integrative Entwicklung

Für die _integrative lokale Entwicklung_ können folgende properties verwendet werden:

        coffeenet:
          application-name: $ApplicationName
          profile: integration
          discovery:
            client:
              service-url:
                defaultZone: http://localhost:8761/eureka/ *
            instance:
              hostname: localhost *
              non-secure-port: $PortDeinesServices *
              metadata-map:
                allowedAuthorities: $Rolle1,$Rolle2,$RolleN

Die markierten (*) Eigenschaften werden mit Standardwerten belegt und müssen für die _integrative lokale Entwicklung_ nicht zwingend angegeben werden.
Daraus ergibt sich folgende noch zu konfigurierende Eigenschaften:

        coffeenet:
          application-name: $ApplicationName
          profile: integration
          discovery:
            instance:
              metadata-map:
                allowedAuthorities: $Rolle1,$Rolle2,$RolleN

Wobei 'allowedAuthorities' nur angegeben werden muss, wenn die Applikation nur für bestimmte Rollen in der Navigation-Bar angezeigt werdne soll.

### Produktiv

Produktiv sollte dann statt 'http://localhost:8761/eureka/' der produktive Discovery-Server unter 'https://discovery.synyx.coffee/eureka/' eingesetzt werden.

        coffeenet:
          application-name: $ApplicationName
          profile: integration
          discovery:
            client:
              service-url:
                defaultZone: https://discovery.synyx.coffee/eureka/
            instance:
              hostname: $deineApplicationHostname (z.B. tippspiel.synyx.coffee)
              non-secure-port: $PortDeinesServices
              home-page-url: $deineApplicationUrl (z.B. https://tippspiel.synyx.coffee)
              metadata-map:
                allowedAuthorities: $Rolle1,$Rolle2,$RolleN


Über die Eigenschaft 'allowedAuthorities' gibt man die Rolle an, welcher ein Nutzer benötigt um die Applikation verwenden.
Anhand von dieser Information wird die Applikation automatisch aus der Liste der Anwendungen entfernt, sofern der Nutzer diese Berechtigung nicht hat.

## Aktivierung der Service Discovery

Die Service Discovery wird automatisch aktiviert sofern ihr in eurer application.[properties|yml] die option

        coffeenet:
          profile: integration

gesetzt habt. Falls diese Einstellung auf 'development' gestellt ist wird ein Mock Service Discovery Server verwendet.
