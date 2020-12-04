<%@ page import="ru.itis.dto.UserDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="ru.itis.dto.coders.UserDtoDeserializer" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 29.10.2020
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder
            .registerTypeAdapter(UserDto.class, new UserDtoDeserializer())
            .create();
    boolean isAuth = request.getSession().getAttribute("auth") != null ? (Boolean) request.getSession().getAttribute("auth") : false;
    String json = request.getAttribute("users").toString();
    List<UserDto> users = gson.fromJson(json, new TypeToken<List<UserDto>>() {
    }.getType());
    UserDto sessionUser = (UserDto) request.getSession(false).getAttribute("user");
    List<Integer> usersIdWhereLiked = gson.fromJson(request.getAttribute("likedUsers").toString(),
            new TypeToken<List<Integer>>() {
            }.getType());


%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="../../css/main-page/main.css">
    <link rel="stylesheet" href="../../css/main-page/user-list.css">
    <link rel="stylesheet" href="../../css/main-page/search-panel.css">
    <link rel="stylesheet" href="../../css/common/dialog-window.css">
    <link rel="stylesheet" href="../../css/common/left-menu.css">
    <link rel="stylesheet" href="../../css/common/main.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="../../js/main.js"></script>
    <script src="../../js/left-menu.js"></script>
    <style>
        <%if (!isAuth) {%>
        .user-card-like > * {
            cursor: default !important;
        }

        <%}%>
    </style>
</head>
<body>

<div id="black-plug"></div>

<jsp:include page="../dialog-windows/sign-in.jsp"/>
<jsp:include page="../dialog-windows/user-card.jsp"/>
<jsp:include page="../dialog-windows/filters.jsp"/>


<jsp:include page="left-menu.jsp"/>


<!-- SEARCH PANEL -->
<div id="search-panel"
     style="<%= isAuth ? "grid-template-columns: 1fr 1fr;" : ""  %>">

    <div id="search-user-by-firstname-and-lastname"
         style="<%= isAuth ? "" : "display: none"  %>">
        <input id="life-search" name="search-user" type="text" placeholder="Поиск по имени/фамилии"
               autocomplete="false">
    </div>
    <button style="<%= isAuth ? "" : "display: none"  %>"
            id="show-window-for-filters" class="typical-btn">Фильтры
    </button>

    <button style="<%= isAuth ? "display: none" : ""  %>"
            id="show-sign-in-window" class="typical-btn">Войти и познакомиться
    </button>

</div>

<!-- CONTENT -->
<div id="content">
    <ul id="list-of-users">
        <% for (int i = 0; i < users.size(); i++) {
            if (!isAuth || !users.get(i).getId().equals(((UserDto) request.getSession().getAttribute("user")).getId())) {%>
        <li <% if (users.get(i).getGender().equals("Ж")) { %> class="female" <% } %>>
            <img data-id="<%= i %>" src="/user-photo?id=<%= users.get(i).getId() %>" alt="">
        </li>
        <% }
        } %>
    </ul>
</div>

<jsp:include page="footer.jsp"/>

<script>
    $(document).ready(function () {
        let users = <%=json%>;
        let likedUsers = <%= usersIdWhereLiked %>;
        $("#list-of-users").on('click', 'li img', function () {
            let currentUser = users[this.dataset.id];
            $("#user-photo").attr("src", "/user-photo?id=" + currentUser.id);
            $("#user-card-info > h2").text(currentUser.firstName + " " + currentUser.lastName);
            $("#user-age").text(currentUser.age);
            $("#user-city").text(currentUser.city);
            $("#user-short-about").text(currentUser.shortAbout);
            $(".user-card-like span").text(currentUser.countLikes);
            $("#param-user-id").attr("value", currentUser.id);
            $(".user-card-like").attr("data-uid", currentUser.id);
            if (likedUsers.includes(currentUser.id)) {
                $(".user-card-like img").attr("src", "../images/active_like.png");
            } else {
                $(".user-card-like img").attr("src", "../images/disactive_like.png");
            }
        });

        <% if(isAuth) {
            UserDto currentUser = (UserDto)request.getSession().getAttribute("user");
        %>
        $(".user-card-like").click(function () {
            let currentLike = this;
            let countLikes = Number($(currentLike).children("span").text());
            let uid = Number(currentLike.dataset.uid);
            $.ajax({
                method: "POST",
                data: {
                    liked: "",
                    from_user_id: <%= sessionUser.getId() %>,
                    to_user_id: currentLike.dataset.uid,
                    count_likes: countLikes
                },
                success: function () {
                    if (likedUsers.includes(uid)) {
                        $(".user-card-like img").attr("src", "../images/disactive_like.png");
                        $(".user-card-like span").text(countLikes - 1);
                        for (let i = likedUsers.length - 1; i >= 0; i--) {
                            if (likedUsers[i] === uid) {
                                likedUsers.splice(i, 1);
                            }
                        }
                    } else {
                        $(".user-card-like img").attr("src", "../images/active_like.png");
                        $(".user-card-like span").text(countLikes + 1);
                        likedUsers.push(uid);
                    }
                }
            });
        });

        $("#life-search").on('input', function () {
            $("#list-of-users").empty();
            let currentStr = this.value.trim().toLowerCase();

            users.forEach(function (user, i) {
                let nameLastname = (user.firstName + " " + user.lastName).toLowerCase();
                let lastnameName = (user.lastName + " " + user.firstName).toLowerCase();

                if(
                    (nameLastname.search(currentStr) !== -1 || lastnameName.search(currentStr) !== -1) &&
                    user.id !== <%= currentUser.getId() %>
                ) {
                    let userHtml = '<li '+(user.gender === "Ж" ? 'class="female"' : "")+'><img data-id="'+i+'" src="/user-photo?id='+user.id+'" alt=""></li>';
                    $("#list-of-users").append(userHtml);
                }
            });

        });
        <% } %>

    });
</script>

</body>
</html>