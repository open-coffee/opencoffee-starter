# Spring Boot Starter - Application Starter

## Beschreibung
Dieser Starter bietet die Integration in das CoffeeNet und beinhaltet diese Komponenten:
 * [Single-Sign-On](https://gitlab.synyx.de/coffeenet/coffeenet-starter-sso/blob/master/README.md)
 * [Service Discovery](https://gitlab.synyx.de/coffeenet/coffeenet-starter-discovery/blob/master/README.md)
 * [Logging](https://gitlab.synyx.de/coffeenet/coffeenet-starter-logging/blob/master/README.md)
 * [Navigation Bar](https://gitlab.synyx.de/coffeenet/coffeenet-navigation-bar/blob/master/README.md)

Falls ihr nicht alle dieser Komponenten benötigt und zum Beispiel keine Authentifizierung eures Services braucht.
Dann könnt ihr einfach die einzelnen Starter zu diesen Komponenten verwenden.

### CoffeeNet Banner
Ihr bekommt mit diesem Starter automatisch einen 'CoffeeNet Banner', welcher euch beim Starten eurer Anwendung angezeigt wird.
Falls ihr diesen überschreiben wollt, könnt ihr einen eigenen Banner (banner.txt) unter `src/main/resources` legen.

## Repository
        <repositories>
          <repository>
            <id>releases.nexus.synyx.de</id>
            <url>http://nexus.synyx.de/content/repositories/releases</url>
          </repository>
        </repositories>

Bitte sicherstellen, dass das synyx nexus release repository hinterlegt ist.