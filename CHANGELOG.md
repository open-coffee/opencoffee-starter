# Changelog 

### 0.17.0
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
