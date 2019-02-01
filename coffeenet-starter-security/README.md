# CoffeeNet Starter - Security

This CoffeeNet starter secures your application with
spring security and provides access with OAuth2 and the
CoffeeNet Auth Server.
By default all endpoints are secured, but can be configured.

## Getting started

This is a module in the starter set, so you first need to declare your project
as a child of the starter `parent` by editing the `pom.xml` file.

```xml
<parent>
    <groupId>rocks.coffeenet</groupId>
    <artifactId>coffeenet-starter-parent</artifactId>
    <version>${parent.version}</version>
    <relativePath />
</parent>
```

Now you can enable security in your project, by first adding the dependency:

```xml
<dependency>
    <groupId>rocks.coffeenet</groupId>
    <artifactId>starter-security</artifactId>
</dependency>
```

In order to get everything up and running there are some requirements that
your project must fulfill.


## Configuration

```yaml
coffeenet:
  application-name:
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
```

The configuration with their default values.


### In production

In production you need to declare all `*-uri` and `*-url` properties to point
to your OAuth2 server instance (${authServerURL}).

You need to specify your OAuth2 client credentials (${clientId} and ${clientSecret}),
that were provided by the OAuth2 service, under `coffeenet.security.client`

```yaml
coffeenet:
  profile: integration
  security:
    resource:
      user-info-uri: https://${authServerURL}/user
    client:
      client-id: ${clientId}
      client-secret: ${clientSecret}
      user-authorization-uri: https://${authServerURL}/oauth/authorize
      access-token-uri: https://${authServerURL}/oauth/token
    logout-success-url: https://${authServerURL}/logout
```

## Single-Sign-On

The Single-Sign-On mechanism is activated if

```yaml
coffeenet:
  profile: integration
```

is set to `integration`. That means that the your application will
search at the given uri/urls under `coffeenet.security` for a
OAuth2 Server and tries to authenticate and authorize any request.

If you are in development mode

```yaml
coffeenet
  profile: development
```

there will be just a form login and no external dependencies/services are needed.

| User       | Passwort   | Email           | Rollen                 |
| ---------- |------------|-----------------| :---------------------:|
| admin      | admin      | admin@coffeenet | COFFEENET-ADMIN & USER |
| user       | user       | user@coffeenet  | USER                   |


## Manually configure security

If you want to change the default security configuration and maybe
want to not secure an endpoints e.g. than you can create your own beans.

### Integration mode

Create your own `MySecurityConfig` bean, extend [IntegrationCoffeeNetWebSecurityConfigurerAdapter](https://github.com/coffeenet/coffeenet-starter/blob/master/coffeenet-autoconfigure/src/main/java/coffee/synyx/autoconfigure/security/config/IntegrationCoffeeNetWebSecurityConfigurerAdapter.java)
and expose it via `@Bean` or via `@Configuration` like in the example below.

To use provided OAuth2 Single-Sign-On mechanism just call `enableSso(http)` and
than you can configure your application with the default spring security behaviour.

```java
    @Configuration
    @ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = "integration")
    public class MySecurityConfig extends IntegrationCoffeeNetWebSecurityConfigurerAdapter {

      @Override
      public void configure(HttpSecurity http) throws Exception {

        enableSso(http)
            .authorizeRequests()
            ...
      }
    }
```

### Development mode

```java
@Configuration
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = "development", matchIfMissing = true)
public class MyDevSecurityConfig extends DevelopmentCoffeeNetWebSecurityConfigurerAdapter {

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

If you want to override the CoffeeNet default development securiy configuration just
create a bean `MyDevSecurityConfig`, extend from [DevelopmentCoffeeNetWebSecurityConfigurerAdapter](https://github.com/coffeenet/coffeenet-starter/blob/master/coffeenet-autoconfigure/src/main/java/coffee/synyx/autoconfigure/security/config/DevelopmentCoffeeNetWebSecurityConfigurerAdapter.java)
and expose it via `@Bean` or via `@Configuration` like in the example below.
