<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 29.10.2020
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form method="post" action="/signUp">
    <input name="firstName" type="text"
           placeholder="Enter name">
    <input name="lastName" type="text"
           placeholder="Enter lastname">
    <input name="email" type="text"
           placeholder="Enter your email">
    <input name="password" type="password"
           placeholder="Enter password">
    <input type="submit" value="Registration">
</form>
</body>
</html>
