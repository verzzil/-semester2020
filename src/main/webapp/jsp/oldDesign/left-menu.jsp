<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Boolean isAuth = request.getSession().getAttribute("auth") != null ? (Boolean) request.getSession().getAttribute("auth") : false;
%>
<!-- LEFT MENU -->
<nav>
    <img src="../../images/menured.png" alt="" width="20" style="<%= isAuth ? "" : "display: none" %>">
    <a href="/" id="logo"><img src="../../images/logo.png"></a>
</nav>
<ul id="menu">
    <li>
        <form action="/profile" method="post">
            <input type="submit" value="Мой профиль">
        </form>
    </li>
    <li>
        <form action="/message-list" method="post">
            <input type="submit" value="Мои сообщения">
        </form>
    </li>
    <li>
        <a href="/create-thought">Создать мыслю</a>
    </li>
    <li>
        <a href="/settings">Настройки</a>
    </li>
    <li>
        <form action="/" method="post">
            <input type="hidden" name="logout">
            <input type="submit" value="Выйти">
        </form>
    </li>
</ul>