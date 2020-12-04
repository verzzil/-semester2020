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
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="../../css/common/left-menu.css">
    <link rel="stylesheet" href="../../css/common/main.css">
    <link rel="stylesheet" href="../../css/registration/main.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="../../js/left-menu.js"></script>
</head>
<body>

<jsp:include page="left-menu.jsp"/>

<div id="registration">
    <h2>Регистрация</h2>
    <form method="post" action="/signUp">
        <input name="firstName" type="text"
               placeholder="Введите имя">
        <input name="lastName" type="text"
               placeholder="Введите фамилию">
        <input name="email" type="text"
               placeholder="Введите email">
        <input name="age" type="number"
               placeholder="Введите Ваш возраст">
        <input name="city" type="text" placeholder="Введите ваш город">
        <label for="gender">Выберете пол: </label>
        <select name="gender" id="gender">
            <option value="М">Мужской</option>
            <option value="Ж">Женский</option>
        </select>
        <input name="password" type="password"
               placeholder="Enter password">
        <input type="submit" value="Регистрация" class="typical-btn">
    </form>
</div>

</body>
</html>
