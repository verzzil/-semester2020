<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 17.11.2020
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="dialog-for-user-card">
    <img src="../images/2.jpg" id="user-photo">

    <div id="user-card-info">
        <h2></h2>
        <div id="user-card-age-info">
            <label for="user-age">Возраст:</label>
            <span id="user-age">20</span>
        </div>
        <div id="user-card-city-info">
            <label for="user-city">Город:</label>
            <span id="user-city">Kazan</span>
        </div>
        <div id="user-card-short-about-info">
            <label for="user-short-about">О себе:</label>
            <span id="user-short-about">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ratione, similique.</span>
        </div>
        <div id="user-card-like-profile">
            <form action="/profile" method="post">
                <input type="hidden" name="user-id" id="param-user-id">
                <input type="submit" value="Перейти в профиль"
                       class="typical-btn" style="padding: 10px">
            </form>
            <div class="user-card-like">
                <img src="../images/disactive_like.png" width="20">
                <span>100</span>
            </div>
        </div>
    </div>

</div>
