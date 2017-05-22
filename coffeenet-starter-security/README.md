# CoffeeNet Starter - Security

Dieser CoffeeNet-Starter sichert automatisch die Anwendung innerhalb des CoffeeNets anhand von Single-Sign-On ab.
Standardmäßig werden alle Endpunkte abgesichert. Anhand einer manuell erstellten Konfiguration in der Anwendung kann 
diese überschrieben werden.


## Konfiguration

Der CoffeeNet-Starter Security hängt sich standardmäßig an die Eigenschaft ```coffeenet.profile```.

```yaml
coffeenet:
  profile: development
  security:
    enabled: true
    logout-success-url: http://localhost:9999/logout
    default-login-success-url:
    default-login-failure-url: /
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


Der CoffeeNet Auth-Server hat folgende Daten hinterlegt für eine lokale integrative Entwicklung:

```yaml
coffeenet:
  security:
    client:
      client-id: coffeeNetClient
      client-secret: coffeeNetClientSecret
```

### Produktiv

Produktiv sollte dann statt 'http://localhost:9999' der produktive Auth-Server unter 'https://auth.synyx.coffee' eingesetzt werden.

```yaml
coffeenet:
  profile: integration
  security:
    resource:
      user-info-uri: https://auth.synyx.coffee/user
    client:
      client-id: ${clientId}
      client-secret: ${clientSecret}
      user-authorization-uri: https://auth.synyx.coffee/oauth/authorize
      access-token-uri: https://auth.synyx.coffee/oauth/token
    logout-success-url: https://auth.synyx.coffee/logout
```

**Zu beachten:**
* *Https* verwenden, ansonsten wird dem redirect in der Anwendung nicht gefolgt
* ${clientId} ersetzen mit der für die Applikation im Auth-Server eingetragenen ID
* ${clientSecret} ersetzen mit dem für die Applikation im Auth-Server eingetragenen Passwort

Bevor die neue Applikation bei dem Auth-Server verwendet werden kann, muss diese Applikation im Auth-Server, mit deren Daten, hinterlegt werden.
Dies kann von jedem Benutzer durchgeführt werden, der die Rolle `COFFEENET-ADMIN` hat.


## Aktivierung des Single-Sign-Ons

Sofern ihr in eurer application.[properties|yml] die option
```yaml
coffeenet:
  profile: integration
```

gesetzt habt, wird der SSO Mechanismus automatisch verwendet. Standardmäßig erfordert jeder Request eine Authentifizierung.

Falls diese Einstellung auf

```yaml
coffeenet
  profile: development
```

gestellt ist, wird statt des Single-Sign-Ons ein Form-Login durchgeführt.
Standardmäßig sind die Zugangsdaten dann folgende:

| User       | Passwort   | Email   | Rollen   |
| ---------- |------------|------------| :-------:|
| admin      | admin      | admin@coffeenet | COFFEENET-ADMIN |
| user       | user       | user@coffeenet | |


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
Wenn eine spezifischere Konfiguration für abzusichernde Requests notwendig ist, kann eine Konfigurationsklasse erstellt werden die von [IntegrationCoffeeNetWebSecurityConfigurerAdapter](https://github.com/coffeenet/coffeenet-starter/blob/master/coffeenet-autoconfigure/src/main/java/coffee/synyx/autoconfigure/security/config/IntegrationCoffeeNetWebSecurityConfigurerAdapter.java)
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
          .withUser("username").password("password").roles("COFFEENET-ADMIN")
        .and()
          .withUser("anotherUsername").password("anotherPassword").roles("EMPLOYEE");
  }
}
```
Wenn für die Dev-Konfiguration z.B. spezfische Rollen benötigt werden, kann eine Konfigurationsklasse erstellt werden, die von [DevelopmentCoffeeNetWebSecurityConfigurerAdapter](https://github.com/coffeenet/coffeenet-starter/blob/master/coffeenet-autoconfigure/src/main/java/coffee/synyx/autoconfigure/security/config/DevelopmentCoffeeNetWebSecurityConfigurerAdapter.java) erbt.
