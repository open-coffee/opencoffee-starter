# CoffeeNet Starter - Discovery

Dieser CoffeeNet-Starter startet die 'Service-Discovery'-Konfiguration einer Anwendung im CoffeeNet.

## Konfiguration

```yaml
coffeenet:
  application-name:
  allowed-authorities:
  profile: development
  discovery:
    enabled: true
    client:
      service-url:
        defaultZone: http://localhost:8761/eureka/
    instance:
      hostname: localhost
      non-secure-port: 8080
      home-page-url:
```

Die Konfigurationsmöglichkeiten mit deren Standardwerte.

`coffeenet.allowed-authorities` Kommaseparierte Liste anhand von dieser wird die Applikation automatisch aus der Liste 
der Anwendungen entfernt, sofern der Nutzer diese Berechtigung nicht besitzt.

Die Service Discovery wird automatisch aktiviert, sofern ihr in eurer application.[properties|yml] die option
```yaml
coffeenet:
  profile: integration
```
gesetzt habt. Falls diese Einstellung auf 'development' gestellt ist wird ein 'Mock Service Discovery Server' verwendet.
