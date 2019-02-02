# CoffeeNet Starter Navigation Thymeleaf

This CoffeeNet starter is dedicated to provide a simple and easy way
to integrate the CoffeeNet navigation behaviour with thymeleaf 2 into your application.

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

Now you can enable the thymeleaf navigation bar in your project,
by first adding the dependency:

```xml
<dependency>
    <groupId>rocks.coffeenet</groupId>
    <artifactId>coffeenet-starter-navigation-thymeleaf</artifactId>
</dependency>
```

In order to get everything up and running there are some requirements that
your project must fulfill.


## Usage

This example will help you to understand how to use the starter.
The `thymeleaf-layout-dialect` is used in the `<html>` element
to mesh your application specific content with the default layout of the CoffeeNet.
All you have to do is to provide your information into the elements.

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorator="coffeenet/_layout">

<head>
    <!-- your head code belongs here -->
</head>

<body>
    <main layout:fragment="coffeenet-main">
        <!-- your main code belongs here -->
    </main>

    <th:block layout:fragment="coffeenet-scripts">
        <!-- your scripts belongs here -->
    </th:block>
</body>
</html>
```

## Configuration

see the navigation configuration in the [starter navigation readme](./../coffeenet-starter-navigation/README.md)
