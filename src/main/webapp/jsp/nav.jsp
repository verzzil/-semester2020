<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 30.11.2020
  Time: 2:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Boolean isAuth = request.getSession().getAttribute("auth") != null ? (Boolean) request.getSession().getAttribute("auth") : false;
%>
<div id="plug"></div>

<nav>
    <% if (!isAuth) {%>
    <div id="signIn">
        <img src="./images/signin.png" alt="" width="35">
        <h4>Sign In</h4>
    </div>
    <% } else {%>
    <div id="menu">
        <img src="./images/menu.png" width="35" alt="">
        <h4>MENU</h4>
    </div>
    <div id="hideMenu">
        <ul>
            <li id="openMyProfile">
                My Profile
                <form action="/profile" method="post">

                </form>
            </li>
            <li id="openMessages">
                <a href="/chat">My Messages</a>
            </li>
            <li id="openCreateThought">
                <a href="/create-thought" style="padding: 20px 0;">Create thought</a>
            </li>
            <li>
                <a href="/settings">Settings Profile</a>
            </li>
            <li id="logout">
                Logout
                <form action="/" method="post">
                    <input type="hidden" name="logout">
                    <input type="submit" value="Выйти">
                </form>
            </li>
        </ul>
    </div>
    <% } %>
    <div id="logo">
        <a href="/"><img src="./images/logo.png" alt=""></a>
    </div>
    <% if (request.getAttribute("mainPage") != null) {%>
    <div id="search">
        <img src="images/loop1.png" width="35">
        <div id="searchBlock">
            <button id="showFilters">Filters</button>
            <input id="liveSearchInput" type="text" placeholder="Search by name/lastname">
        </div>
    </div>
    <% } %>
</nav>

<div id="layers">
    <div id="layer1"></div>
    <div id="layer2"></div>
    <div id="layer3"></div>
    <div id="layer4"></div>
    <div id="layer5">
        <img src="../images/close.png" id="closeSignInForm" alt="">
        <form id="signInForm" action="/" method="post">
            <h1>Log in</h1>
            <input type="text" name="email" autocomplete="none" placeholder="Enter email">
            <input type="password" name="password" placeholder="Enter password">
            <input type="hidden" name="sign-in">
            <input type="submit" value="Log in">
        </form>
        <jsp:include page="layerSprite.jsp"/>
        <h3 style="z-index: 20; color: #fff;">If you don't have an account, you can <a style="color: #fff" href="/signUp">register</a></h3>
    </div>
</div>