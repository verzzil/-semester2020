<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 30.11.2020
  Time: 5:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="../css/main.css">
    <link rel="stylesheet" href="../css/singUp.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Fredoka+One&display=swap" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="../js/main.js"></script>
</head>
<body>
<div id="layer5" style="left: 0 !important;">
    <a href="/" id="goToMain"><img src="../images/goToMain.png" height="100" alt=""></a>
    <form action="/signUp" method="post" id="signUpForm">
        <h1>Registration</h1>
        <input name="firstName" type="text"
               placeholder="Введите имя">
        <input name="lastName" type="text"
               placeholder="Введите фамилию">
        <input name="email" type="email"
               placeholder="Введите email">
        <input name="age" type="number"
               placeholder="Введите Ваш возраст">
        <input name="city" type="text" placeholder="Введите ваш город">
        <input name="password" type="password"
               placeholder="Enter password">
        <label for="gender" style="text-align: center; color: #fff;">Выберете пол: </label>
        <select name="gender" id="gender">
            <option value="М">Мужской</option>
            <option value="Ж">Женский</option>
        </select>
        <input type="submit" value="Регистрация">
    </form>
    <jsp:include page="layerSprite.jsp" />

</div>

</body>
</html>
