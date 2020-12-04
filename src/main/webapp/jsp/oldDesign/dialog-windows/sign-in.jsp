<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 17.11.2020
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="dialog-window-for-sign-in">
    <h1>Вход/Регистрация</h1>
    <form action="/" method="post">
        <input type="text" name="email" placeholder="Введите email" autocomplete="none">
        <input type="password" name="password" placeholder="Введите ваш пароль">
        <input type="hidden" name="sign-in">
        <input type="submit" class="typical-btn" value="Вход">
    </form>
    <span>Нет аккаунта? Вы можете пройти <a href="/signUp">регистрацию</a></span>
</div>
