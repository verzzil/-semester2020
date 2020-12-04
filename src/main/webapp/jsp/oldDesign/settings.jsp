<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 18.11.2020
  Time: 0:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Настройки профиля</title>
    <link rel="stylesheet" href="../../css/common/left-menu.css">
    <link rel="stylesheet" href="../../css/common/main.css">
    <link rel="stylesheet" href="../../css/registration/main.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="../../js/left-menu.js"></script>
</head>
<body>

<jsp:include page="left-menu.jsp" />

<div id="registration">
    <h1>Настройки профиля</h1>
    <form action="/settings" method="post">
        <h3>Изменить информацию о себе</h3>
        <input type="hidden" name="name-lastname-city">
        <input type="text" placeholder="Изменить имя" name="name">
        <input type="text" placeholder="Изменить фамилию" name="lastname">
        <input type="text" placeholder="Изменить город" name="city">
        <input type="submit" value="Применить" class="typical-btn">
    </form>
    <form action="/settings" method="post">
        <h3>Изменить пароль</h3>
        <input type="hidden" name="change-pass">
        <input type="password" placeholder="Введите старый пароль" name="old-password">
        <input type="password" placeholder="Введите новый пароль" name="new-password">
        <input type="password" placeholder="Повторите новый пароль" name="repeat-new-password">
        <input type="submit" value="Изменить пароль" class="typical-btn">
    </form>
    <form action="/settings" method="post" enctype="multipart/form-data">
        <h3>Изменить фото профиля</h3>
        <input type="file" name="file">
        <input type="submit" value="Загрузить фото профиля" class="typical-btn">
    </form>

</div>

</body>
</html>