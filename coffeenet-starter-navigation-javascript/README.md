# CoffeeNet Starter Navigation Javascript

This CoffeeNet starter is dedicated to provide a simple and easy way to
integrate the CoffeeNet web navigation behaviour with javascript as your rendering engine.

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

Now you can enable the javascript navigation bar in your project, by first adding the
dependency:

```xml
<dependency>
    <groupId>coffee.synyx</groupId>
    <artifactId>starter-navigation-javascript</artifactId>
</dependency>
```

In order to get everything up and running there are some requirements that
your project must fulfill.


## Usage

Just add the html snipped listed below:

```html
<html>
<head>
    <!-- Your header stuff here -->
</head>

<body>
    <!-- CoffeeNet header -->
    <header id="coffeenet-header"></header>

    <!-- Your html here -->

    <script src="/webjars/navigation-bar/bundle.min.js"></script>
    <!-- Your scripts here -->
</body>
</html>
```

## Configuration

see the navigation configuration in the [starter navigation readme](./../coffeenet-starter-navigation/README.md)
