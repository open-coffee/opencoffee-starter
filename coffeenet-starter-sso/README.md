# Spring Boot Starter - Single-Sign-On (SSO)

## Beschreibung
Diese Abhängigkeit startet automatisch die Konfiguration des 'Single-Sign-On' einer Spring-Boot Anwendung.


## Verbindungsinformationen

Die Verbindungsinformationen des Auth-Server werden in der application.[properties/yml] hinterlegt

### Lokale Entwicklung (Mock)

Für die _gemockte lokale Entwicklung_ reicht es folgende properties zu verwenden:

         coffeenet:
           profile: development

### Lokale integrative Entwicklung

Für die _integrative lokale Entwicklung_ können folgende properties verwendet werden:

        coffeenet:
          profile: integration
          security:
            resource:
              id: oauth2-resource *
              user-info-uri: http://localhost:9999/user *
            client:
              client-id: coffeeNetClient
              client-secret: coffeeNetClientSecret
              user-authorization-uri: http://localhost:9999/oauth/authorize *
              access-token-uri: http://localhost:9999/oauth/token *
            logout-success-url: http://localhost:9999/logout *

Die markierten (*) Eigenschaften werden mit Standardwerten belegt und müssen für die _integrative lokale Entwicklung_ nicht zwingend angegeben werden.
Daraus ergibt sich folgende noch zu konfigurierende Eigenschaften:

        coffeenet:
          profile: integration
          security:
            client:
              client-id: coffeeNetClient
              client-secret: coffeeNetClientSecret

### Produktiv

Produktiv sollte dann statt 'http://localhost:9999' der produktive Auth-Server unter 'https://auth.synyx.coffee' eingesetzt werden.

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

Zu beachten:
* *Https* verwenden ansonsten wird dem redirect in der Anwendung nicht gefolgt.
* ${clientId} ersetzen mit der für die Applikation im Auth-Server eingetragenen ID
* ${clientSecret} ersetzen mit dem für die Applikation im Auth-Server eingetragenen Passwort

Bevor die neue Applikation bei dem Auth-Server verwendet werden kann, muss die neue Applikation im Auth-Server hinterlegt werden.
Dies kann von jedem Benutzer durchgeführt werden, der die Rolle "COFFEENET-ADMIN" hat.


## Aktivierung des Single-Sign-Ons

Sofern ihr in eurer application.[properties|yml] die option

        coffeenet:
          profile: integration

gesetzt habt, wird der SSO Mechanismus automatisch verwendet. Standardmäßig erfordert jeder Request eine authentifizierung.
Falls diese Einstellung auf 'development' gestellt ist wird statt des SSO ein Form-Login durchgeführt.
Standardmäßig sind die Zugangsdaten im development Modus folgende:

| User       | Passwort   | Rollen   |
| ---------- |------------| :-------:|
| admin      | admin      | SYSADMIN |
| user       | user       | EMPLOYEE |
| coffeenet  | coffeenet  | COFFEENET-ADMIN |



## Konfigurationen anpassen

Wenn eine spezifischere Konfiguration für abzusichernde Requests notwendig ist, kann eine Konfigurationsklasse erstellt werden die von [IntegrationCoffeeNetWebSecurityConfigurerAdapter](https://gitlab.synyx.de/coffeenet/coffeenet-autoconfigure/blob/master/src/main/java/coffee/synyx/autoconfigure/security/config/integration/IntegrationCoffeeNetWebSecurityConfigurerAdapter.java)
erbt.

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

Wenn für die Dev-Konfiguration z.B. spezfische Rollen benötigt werden, kann eine Konfigurationsklasse erstellt werden, die von [DevelopmentCoffeeNetWebSecurityConfigurerAdapter](https://gitlab.synyx.de/coffeenet/coffeenet-autoconfigure/blob/master/src/main/java/coffee/synyx/autoconfigure/security/config/development/DevelopmentCoffeeNetWebSecurityConfigurerAdapter.java) erbt.

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
## Repository

        <repositories>
          <repository>
            <id>releases.nexus.synyx.de</id>
            <url>http://nexus.synyx.de/content/repositories/releases</url>
          </repository>
        </repositories>

Bitte sicherstellen, dass das synyx nexus release repository hinterlegt ist.
