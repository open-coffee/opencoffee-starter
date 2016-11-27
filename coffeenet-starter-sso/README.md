# CoffeeNet Starter - Security

Dieser Starter startet automatisch die 'Security'-Konfiguration einer Anwendung des CoffeeNets.


## Konfiguration

Der CoffeeNet-Starter Security hängt sich standardmäßig an die Eigenschaft ```coffeenet.profile```.

```yaml
coffeenet:
  profile: development
  security:
    resource:
      id: oauth2-resource
      user-info-uri: http://localhost:9999/user
    client:
      client-id:
      client-secret:
      user-authorization-uri: http://localhost:9999/oauth/authorize
      access-token-uri: http://localhost:9999/oauth/token
    logout-success-url: http://localhost:9999/logout
```

Die Konfigurationsmöglichkeiten mit deren Standardwerte.


Der CoffeeNet Auth-Server hat folgende Daten hinterlegt für eine lokale Entwicklung:

```yaml
coffeenet:
  security:
    client:
      client-id: testClient
      client-secret: testClientSecret
```

### Produktiv

Produktiv sollte dann statt 'http://localhost:9999' der produktive Auth-Server unter 'https://auth.synyx.coffee' eingesetzt werden.

```yaml
coffeenet:
  profile: integration
  security:
    resource:
      id: oauth2-resource
      user-info-uri: https://auth.synyx.coffee/user
    client:
      client-id: ${clientId}
      client-secret: ${clientSecret}
      user-authorization-uri: https://auth.synyx.coffee/oauth/authorize
      access-token-uri: https://auth.synyx.coffee/oauth/token
    logout-success-url: https://auth.synyx.coffee/logout
```

Zu beachten:
* *Https* verwenden ansonsten wird dem redirect in der Anwendung nicht gefolgt.
* ${clientId} ersetzen mit der für die Applikation im Auth-Server eingetragenen ID
* ${clientSecret} ersetzen mit dem für die Applikation im Auth-Server eingetragenen Passwort

Bevor die neue Applikation bei dem Auth-Server verwendet werden kann, muss die neue Applikation im Auth-Server hinterlegt werden.
Dies kann von jedem Benutzer durchgeführt werden, der die Rolle "COFFEENET-ADMIN" hat.


## Aktivierung des Single-Sign-Ons

Sofern ihr in eurer application.[properties|yml] die option
```java
coffeenet:
  profile: integration
```

gesetzt habt, wird der SSO Mechanismus automatisch verwendet. Standardmäßig erfordert jeder Request eine authentifizierung.
Falls diese Einstellung auf `coffeenet.profile: development` gestellt ist, wird statt des SSO ein Form-Login durchgeführt.
Standardmäßig sind die Zugangsdaten dann folgende:

| User       | Passwort   | Rollen   |
| ---------- |------------| :-------:|
| admin      | admin      | SYSADMIN |
| user       | user       | EMPLOYEE |
| coffeenet  | coffeenet  | COFFEENET-ADMIN |


## Verhalten anpassen

### Integration

```java
    @Configuration
    @ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = "integration") //Wichtig, damit diese Konfiguration nur im Produktiv-Modus herangezogen wird
    public class SecurityConfig extends IntegrationCoffeeNetWebSecurityConfigurerAdapter {

      @Override
      public void configure(HttpSecurity http) throws Exception {

        enableSso(http) // Aktiviert die Verwendung des Auth-Servers. Dabei werden noch keine gesicherten URLs definiert!
            .authorizeRequests()
            ...//Die gewünschte Konfiguration
      }
    }
```
Wenn eine spezifischere Konfiguration für abzusichernde Requests notwendig ist, kann eine Konfigurationsklasse erstellt werden die von [IntegrationCoffeeNetWebSecurityConfigurerAdapter](https://gitlab.synyx.de/coffeenet/coffeenet-autoconfigure/blob/master/src/main/java/coffee/synyx/autoconfigure/security/config/integration/IntegrationCoffeeNetWebSecurityConfigurerAdapter.java)
erbt.


### Development

```java
@Configuration
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = "development", matchIfMissing = true)
public class ExampleDevSecurityConfig extends DevelopmentCoffeeNetWebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth
        .inMemoryAuthentication()
          .withUser("role1").password("role1").authorities("ROLE1")
        .and()
          .withUser("role2").password("role2").authorities("ROLE2");
  }
}
```
Wenn für die Dev-Konfiguration z.B. spezfische Rollen benötigt werden, kann eine Konfigurationsklasse erstellt werden, die von [DevelopmentCoffeeNetWebSecurityConfigurerAdapter](https://gitlab.synyx.de/coffeenet/coffeenet-autoconfigure/blob/master/src/main/java/coffee/synyx/autoconfigure/security/config/development/DevelopmentCoffeeNetWebSecurityConfigurerAdapter.java) erbt.
