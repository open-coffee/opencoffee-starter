# CoffeeNet Starter Web - Thymeleaf


## Example

```thymeleafexpressions
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorator="coffeenet/_layout">

<head>
  <!-- your head code belongs here -->
</head>

<body>
<main layout:fragment="main">
  <!-- your main code belongs here -->
</main>

<th:block layout:fragment="scripts">
  <!-- your scripts belongs here -->
</th:block>
</body>
</html>

```