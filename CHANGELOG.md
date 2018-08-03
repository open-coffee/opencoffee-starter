# Changelog 

### 0.32.0-SNAPSHOT
* Dependency
  * Upgrade `logback-gelf-appender` to `1.5`
  * Upgrade `spring boot` to `1.5.15`

### 0.31.0
* Add
  * Case insensitive ordering of apps in the navigation bar
* Dependency
  * Upgrade `spring boot` to `1.5.14`

### 0.30.0
* Dependency
  * Upgrade `spring boot` to `1.5.13`
* Fix
  * Always add `OAuth2ClientContextFilter` if security starter is in use


### 0.29.0
* Dependency
  * Upgrade `logback-gelf-appender` to `1.4`
  * Add jacoco and coveralls for test coverage
  * Upgrade `maven wrapper` to `3.5.3`
  * Upgrade `spring boot` to `1.5.12`

### 0.28.0
* Add
  * Add new CoffeeNet Logo for favicon and
    in the navigation
* Dependency
  * Upgrade `maven wrapper` to `3.5.2`
  * Upgrade `spring boot` to `1.5.10`
  * Upgrade `spring-cloud-dependencies` to `Dalston.SR5`

### 0.27.1
* Dependency
  * Downgrade `spring-cloud-dependencies` to `Dalston.SR4`
    * Because of problems with the `EurekaInstanceConfigBean` generation

### 0.27.0
* Dependency
  * Upgrade `spring boot` to `1.5.9`
  * Upgrade to `spring-cloud-dependencies` to `Edgware.RELEASE`
    * For changelog: https://github.com/spring-projects/spring-cloud/wiki/Spring-Cloud-Edgware-Release-Notes

### 0.26.0
* Dependency
  * PUT parameters will not be sent -> back to spring version 4.3.9
    (https://jira.spring.io/browse/SPR-15828)
  * Upgrade to `spring-cloud-dependencies` to `Dalston.SR3`

### 0.25.0
* Upgrade `spring boot` to `1.5.6`
* Upgrade `spring-cloud-dependencies` to `Dalston.SR2`
* Add version information of starter and application
  in navigation
* Fix not usable `spring-boot-devtools` dependency
  * Declare
    `CoffeeNetDiscoveryInstanceProperties`,
    `CoffeeNetDiscoveryClientProperties`
    in public scope because of spring devtools cannot wrap
    the class properly in non-public scope

### 0.24.0
* Upgrade `logback-gelf-appender` to `1.3.1`
* Fix not available discovery dependency in
  autoconfigure for relations between auto configures
* Fix auto completion of `coffeenet.discovery.*` in your IDE
* Renaming
  * `coffeenet-starter-web` -> `coffeenet-starter-navigation`
  * `coffeenet-starter-web-javascript` -> `coffeenet-starter-navigation-javascript`
  * `coffeenet-starter-web-thymeleaf` -> `coffeenet-starter-navigation-thymeleaf`

### 0.23.0
* Upgrade to `spring-cloud-dependencies` to Dalston.SR1
* Upgrade navigation bar to 0.12.1

### 0.22.1
* Use `EurekaDiscoveryClient` as guard for `starter-discovery`
  instead of the `DiscoveryClient` that is also used by other libraries.

### 0.22.0
* Add missing `spring-boot-starter-web` in `starter-web-javascript`
* Upgrade navigation bar to 0.12.0
  * Remove bootstrap dependency

### 0.21.0
* Starters now complete independent usable from each other
* Remove coffeenet-starter-application
* Security Starter: admin and user in development mode both have now the role "USER"
* Upgrade to spring boot 1.5.4

### 0.20.0
* Upgrade to spring-cloud-dependencies Camden.SR7
* New global CoffeeNet banner
* New favicon in the default resource directory
* Rename 'starter-sso' to 'starter-security'

### 0.19.0
* Upgrade to spring boot 1.5.3
* Upgrade to spring-cloud-dependencies Camden.SR6
  * Add @Validated to all @ConfigurationProperties with Validation
* Change thymeleaf "main" fragment name to "coffeenet-main"
* Change thymeleaf "scripts" fragment name to "coffeenet-scripts"
* Prefix thymeleaf intern fragment names with "coffeenet-*"

### 0.18.1
* Add check to start `CoffeeNetWebAutoConfiguration` configuring when `coffeenet-starter-web` is used

### 0.18.0
* Upgrade logback-gelf-appender to 1.3.0
* Add Security (SSO) and Discovery Starter are now optional for the Web Starter
* Fix to mark the opened application in the navigation bar when url only starts with the given url

### 0.17.0
* Upgrade to spring-cloud-dependencies Camden.SR5
* Add parent pom functionality for starters **and** applications
* Add only show "Anwendungen" headline in navigation bar if an application is present
* Fix to mark the opened application in the navigation bar in thymeleaf template
* Fix to improve guards in navigation bar to handle null values

### 0.16.2
* No user is needed to provide `CoffeeNetWeb`

### 0.16.1
* Fix ignoring all service instances that are not from eureka

### 0.16.0
* Upgrade to spring boot 1.5.1
* Add `CoffeeNetAppService` can be queried to find the `CoffeeNetApp` you need
* Fix get `CoffeeNetApps` in discovery starter ordered by their names
* Fix hamburger menu in thymeleaf starter
* Remove bootstrap dependency from navigation bar
* Remove deprecated `coffeenet/user` and `coffeenet/apps` endpoints for the javascript navigation bar


### 0.15.1
* Fix to only configure web starters when security and discovery is available
* Upgrade to Spring Boot 1.4.4
* Upgrade to Spring Cloud Camden.SR4

### 0.15.0
* Use one AutoConfiguration per starter type
  * New package structure
* Add Web Starters
  * Thymeleaf Starter
  * Javascript Starter
