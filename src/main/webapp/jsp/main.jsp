<%@ page import="java.util.List" %>
<%@ page import="ru.itis.dto.UserDto" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%@ page import="ru.itis.dto.coders.UserDtoDeserializer" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 30.11.2020
  Time: 2:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder
            .registerTypeAdapter(UserDto.class, new UserDtoDeserializer())
            .create();

    boolean isAuth = request.getSession().getAttribute("auth") != null ? (Boolean) request.getSession().getAttribute("auth") : false;
    List<UserDto> users = (List<UserDto>) request.getAttribute("users");
    UserDto sessionUser = (UserDto) request.getSession(false).getAttribute("user");
    List<Integer> usersIdWhereLiked = (List<Integer>) request.getAttribute("likedUsers");
    String jsonUsers = gson.toJson(users);


%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Find yourself</title>
    <link rel="stylesheet" href="../css/main.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Fredoka+One&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Bungee+Shade&display=swap" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="../js/main.js"></script>
    <script src="../js/submitForms.js"></script>

    <% if (isAuth) {%>
    <style>
        .countLikes:hover {
            background: #5953FF;
        }
    </style>
    <% } %>
</head>
<body>

<form action="/" method="post" id="filters">
    <input type="hidden" name="filter">
    <h1 style="grid-column-start: 1; grid-column-end: 4;">Filters</h1>
    <label for="gender-option">Пол:</label>
    <select name="gender" id="gender-option" style="grid-column-start: 2; grid-column-end: 4;">
        <option value="any-gender" selected>Любой</option>
        <option value="М">Мужской</option>
        <option value="Ж">Женский</option>
    </select>
    <label for="from_age">Возраст:</label>
    <input name="from-age" type="number" id="from_age" placeholder="От">
    <input name="to-age" type="number" id="to_age" placeholder="До">
    <label for="city-option">Город:</label>
    <select name="city" id="city-option" style="margin-bottom:10px;grid-column-start: 2; grid-column-end: 4;">
        <option value="any-city" selected>Любой</option>
        <option value="Уфа">Уфа</option>
        <option value="Казань">Казань</option>
        <option value="Стерлитамак">Стерлитамак</option>
        <option value="Салават">Салават</option>
        <option value="Нурмина">Нурмина</option>
        <option value="Ереван">Ереван</option>
    </select>

    <input type="submit" id="submit-search-user-by-criteria" style="grid-column-start: 1; grid-column-end: 4;"/>
</form>

<jsp:include page="nav.jsp"/>

<div id="greeting">
    <div id="slogan">
        <h1>Find you love</h1>
        <h1>Find yourself</h1>
    </div>
    <ul id="sprites">
        <li id="chocoSprite"><img src="../images/greeting/choco.png" width="100" alt=""></li>
        <li id="loveMessageSprite"><img src="../images/greeting/greenlovemessage.png" width="80" alt=""></li>
        <li id="handSprite"><img src="../images/greeting/whitehand.png" width="100" alt=""></li>
        <li id="homeSprite"><img src="../images/greeting/home.png" width="100" alt=""></li>
        <li id="loveBookSprite"><img src="../images/greeting/greenlovebook.png" width="100" alt=""></li>
        <li id="loveIsSprite"><img src="../images/greeting/loveis.png" width="160" alt=""></li>
    </ul>
</div>


<div id="content">
    <h1>The time has come. Take action!</h1>
    <ul id="userList">
        <% for (int i = 0; i < users.size(); i++) {%>
        <% if (!isAuth || !users.get(i).getId().equals(((UserDto) request.getSession().getAttribute("user")).getId())) { %>
        <li data-uid="<%= users.get(i).getId() %>">
            <div class="overlay">
                <div class="overBack"></div>
                <h2><%= users.get(i).getFirstName() + " " + users.get(i).getLastName() %>
                </h2>
                <div class="userInfo">
                    <span>Город:</span><span><%= users.get(i).getCity() %></span>
                    <span>Возраст:</span><span><%= users.get(i).getAge() %></span>
                    <span>О себе:</span><span><%= users.get(i).getShortAbout() %></span>
                </div>
                <div class="countLikes <%= isAuth ? "cl" : "" %>" data-uid="<%= users.get(i).getId() %>">
                    <img <%= isAuth ? "class=\"icl\"" : "" %>
                            src="<%= usersIdWhereLiked.contains(users.get(i).getId()) ? "../images/activelike.png" : "../images/like.png" %>"
                            width="15"/>
                    <span <%= isAuth ? "class=\"scl\"" : "" %>><%= users.get(i).getCountLikes() %></span>
                </div>
            </div>
            <img src="/user-photo?id=<%= users.get(i).getId() %>" alt="">
            <form action="/profile" method="post" style="display: none">
                <input type="hidden" name="user-id" value="<%= users.get(i).getId() %>">
            </form>
        </li>
        <% } %>
        <% } %>
    </ul>
</div>

<jsp:include page="footer.jsp"/>


<script>
    $(document).ready(function () {
        <% if(isAuth) { %>
        $(".countLikes").on('click', function () {
            let currentLike = this
            let countLikes = Number($($(currentLike).children('span')).text())
            $.ajax({
                method: "POST",
                data: {
                    liked: "",
                    from_user_id: <%= sessionUser.getId() %>,
                    to_user_id: currentLike.dataset.uid,
                    count_likes: countLikes
                }
            });
            let cur = $(currentLike), curImg = $($(cur).children('img')), curSpan = $($(cur).children('span'));
            if (curImg.attr("src") === "../images/like.png") {
                curImg.attr("src", "../images/activelike.png")
                curSpan.text(countLikes + 1)
            } else {
                curImg.attr("src", "../images/like.png")
                curSpan.text(countLikes - 1)
            }
        });
        <% } %>

        let users = <%= jsonUsers %>;

        $("#liveSearchInput").on('input', function () {
            let currentStr = this.value.trim().toLowerCase();

            users.forEach(function (user, i) {
                let nameLastname = (user.firstName + " " + user.lastName).toLowerCase();
                let lastnameName = (user.lastName + " " + user.firstName).toLowerCase();

                if (
                    nameLastname.search(currentStr) === -1 &&
                    lastnameName.search(currentStr) === -1
                )
                    $("#userList li[data-uid=" + user.id + "]").fadeOut(300);
                else
                    $("#userList li[data-uid=" + user.id + "]").fadeIn(300);

            });
        });

    });
</script>

</body>
</html>