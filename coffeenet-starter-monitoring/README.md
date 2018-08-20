# CoffeeNet Starter - Monitoring

This CoffeeNet starter is designed to add information to the  
`/health` endpoint about the status of CoffeeNet services like  
the Auth Server depending on the starters that are used in the project.

If you use the `coffeenet-security-starter` with the integration profile  
then a check against the configured Auth Server will be done to see
if the Auth Server is available and your project should work correctly


## Getting started

This is a module in the starter set, so you first need to declare your project
as a child of the starter `parent` by editing the `pom.xml` file.

```xml
<parent>
    <groupId>coffee.synyx</groupId>
    <artifactId>coffeenet-starter-parent</artifactId>
    <version>${parent.version}</version>
    <relativePath />
</parent>
```

and adding the repository to receive the dependencies

```xml
<repositories>
  <repository>
    <id>releases.public.nexus.synyx.de</id>
    <url>http://nexus.synyx.de/content/repositories/public-releases</url>
  </repository>
</repositories>
```

Now you can enable monitoring in your project, by first adding the dependency:

```xml
<dependency>
    <groupId>coffee.synyx</groupId>
    <artifactId>starter-monitoring</artifactId>
</dependency>
```

## Configuration
The monitoring starter configuration depends on the CoffeeNet profile `coffeenet.profile`.

It will only add further CoffeeNet `HealthIndiators` based on `AbstractHealthIndicator`  
if the the `coffeenet.profile` is set to `integration` otherwise you are in the `development`  
mode and it would make no sense to lookup the reachability e.g. of the Auth Server.

```yaml
coffeenet:
  monitoring:
    security:
      health-uri: http://localhost:9999/health
```