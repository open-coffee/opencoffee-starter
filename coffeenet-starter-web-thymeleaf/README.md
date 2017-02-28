# CoffeeNet Starter Web - Thymeleaf

This CoffeeNet started is dedicated to provide a simple and easy way to integrate the coffeenet web behaviour with thymeleaf 2 into your application.

## Example

This example will help you to understand how to use the starter. The `thymeleaf-layout-dialect` is used in the `<html>` element to mesh your application specific content with the default layout of the CoffeeNet. All you have to do is to provide your information into the elements.

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
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