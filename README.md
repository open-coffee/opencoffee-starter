[![CoffeeNet: CI/CD](https://github.com/coffeenet/coffeenet-starter/workflows/CoffeeNet:%20CI/CD/badge.svg)](https://github.com/coffeenet/coffeenet-starter/actions?query=workflow%3A%22CoffeeNet%3A+CI%2FCD%22)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/rocks.coffeenet/coffeenet-starter/badge.svg)](https://search.maven.org/search?q=g:rocks.coffeenet%20AND%20a:coffeenet-starter&core=gav)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=rocks.coffeenet:coffeenet-starter&metric=coverage)](https://sonarcloud.io/dashboard?id=rocks.coffeenet:coffeenet-starter)

# CoffeeNet Starters

These projects contains all CoffeeNet starters, which can be used
to integrate an application into the CoffeeNet.

[Security](coffeenet-starter-security/README.md)
provides the authentication against the CoffeeNet auth server.

[Discovery](./coffeenet-starter-discovery/README.md)
provides the integration to distribute and search of CoffeeNet applications.

[Monitoring](./coffeenet-starter-monitoring/README.md)
provides information about the service.

[Navigation Thymeleaf](./coffeenet-starter-navigation-thymeleaf/README.md)
provides the server side rendering of the navigation bar via thymeleaf.

[Navigation Javascript](./coffeenet-starter-navigation-javascript/README.md)
provides client side rendering of the navigation bar with javascript.

## Release

To build a release from a `-SNAPSHOT` version:

- remove `-SNAPSHOT`
```bash
./mvnw org.codehaus.mojo:versions-maven-plugin:2.5:set -DremoveSnapshot=true -DprocessAllModules=true org.codehaus.mojo:versions-maven-plugin:2.5:commit
```

- commit and push with tag

- set new `-SNAPSHOT` version
```bash
./mvnw -f coffeenet-starter-parent/pom.xml org.codehaus.mojo:versions-maven-plugin:2.5:set -DnextSnapshot -DprocessAllModules=true
./mvnw org.codehaus.mojo:versions-maven-plugin:2.5:commit
```

- commit and push


## Architecture

### Parent

```
                                    ------------------
                                    |   Spring Boot  |
                                    ------------------
                                             ↑
                                             |
                                -----------------------------
                                |   CoffeeNet Dependencies  |
                                -----------------------------
                                             ↑
                                             | 
                                  ------------------------
         -----------------------→ |   CoffeeNet Parent   | ←-----------
         |                        ------------------------            |
         |                                                            |
         |                                                            |
         |   --------------------------                  -----------------------------
         |-- |   CoffeeNet Actuator   |                  |   CoffeeNet Application   |
         |   --------------------------                  -----------------------------
         |
         |   -------------------------------
         |-- |   CoffeeNet Autoconfigure   |
         |   -------------------------------
         |   ---------------------------------
         |-- |   CoffeeNet Starter Discovery |
         |   ---------------------------------
         |                 ...
         |   ---------------------------------------------
         |-- |   CoffeeNet Starter Navigation Themeleaf  |
             ---------------------------------------------
```
