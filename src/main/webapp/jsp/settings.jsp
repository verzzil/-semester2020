<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 03.12.2020
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile settings</title>
    <link rel="stylesheet" href="../css/main.css">
    <link rel="stylesheet" href="../css/profileSettings.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Fredoka+One&display=swap" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="../js/main.js"></script>
    <script src="../js/submitForms.js"></script>
</head>
<body>

<jsp:include page="nav.jsp"/>


<div id="layer5" style="left: 0 !important; color: #fff; overflow-y: scroll;
    font-family: 'Fredoka One', cursive; display: grid; grid-template-rows: 200px;">
    <h1>Profile settings</h1>
    <div id="registration" style="z-index: 2;">
        <form action="/settings" method="post">
            <h3>Change information about yourself</h3>
            <input type="text" placeholder="Изменить имя" name="name">
            <input type="text" placeholder="Изменить фамилию" name="lastname">
            <input type="text" style="border:none;" placeholder="Изменить город" name="city">
            <input type="hidden" name="name-lastname-city">
            <input type="submit" value="Применить" class="typical-btn">
        </form>
        <form action="/settings" method="post">
            <h3>Change password</h3>
            <input type="password" placeholder="Введите старый пароль" name="old-password">
            <input type="password" placeholder="Введите новый пароль" name="new-password">
            <input type="password" style="border:none;" placeholder="Повторите новый пароль" name="repeat-new-password">
            <input type="hidden" name="change-pass">
            <input type="submit" value="Изменить пароль" class="typical-btn">
        </form>
        <form action="/settings" method="post" enctype="multipart/form-data">
            <h3>Change profile photo</h3>
            <input type="file" name="file" style="border:none;">
            <input type="submit" value="Загрузить фото профиля" class="typical-btn">
        </form>
        <form action="/settings" method="post">
            <h3>About you</h3>
            <input type="text" name="shortAbout" placeholder="Давай как-нибудь кратенько здесь">
            <input type="text" name="fullAbout" placeholder="Здесь уже всё что душе угодно, только не матерись!">
            <input type="submit" value="Применить">
        </form>
    </div>
    <ul id="layerSprite">
        <li><img src="../images/greeting/choco.png" width="200" alt=""></li>
        <li><img src="../images/greeting/home.png" width="200" alt=""></li>
        <li style="justify-self: end;"><img src="../images/greeting/choco.png" width="200" alt=""></li>
        <li style="justify-self: end;"><img src="../images/greeting/home.png" width="200" alt=""></li>
    </ul>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
