<%@ page import="ru.itis.dto.UserDto" %>
<%@ page import="ru.itis.dto.ArticleDto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 18.11.2020
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UserDto sessionUser = (UserDto) request.getSession(false).getAttribute("user");
    UserDto user = request.getAttribute("currentUser") != null ? (UserDto) request.getAttribute("currentUser") : (UserDto) request.getSession().getAttribute("user");
    boolean isMyPage = request.getAttribute("currentUser") == null ? true : false;
    boolean isAuth = request.getSession().getAttribute("auth") != null ? (Boolean) request.getSession().getAttribute("auth") : false;

    List<ArticleDto> userArticles = (List<ArticleDto>) request.getAttribute("currentUserArticles");
    List<Integer> articlesIdWhereLiked = (List<Integer>) request.getAttribute("likedArticles");
    List<Integer> usersIdWhereLiked = (List<Integer>) request.getAttribute("likedUsers");
    List<String> namesWhereLiked = (List<String>) request.getAttribute("namesWhereLiked");
%>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= user.getFirstName() + " " + user.getLastName() %>
    </title>
    <link rel="stylesheet" href="../../css/profile-page/main.css">
    <link rel="stylesheet" href="../../css/profile-page/profile.css">
    <link rel="stylesheet" href="../../css/common/left-menu.css">
    <link rel="stylesheet" href="../../css/common/main.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="../../js/profile.js"></script>
    <script src="../../js/left-menu.js"></script>
    <style>
        <% if (!isAuth || isMyPage) {%>
        .user-card-like > * {
            cursor: default !important;
        }

        <%}%>
    </style>
</head>
<body>

<jsp:include page="left-menu.jsp"/>

<!-- CONTENT -->
<div id="content">
    <div id="profile">

        <img src="/user-photo?id=<%= user.getId() %>" id="profile-photo">
        <div id="profile-info">
            <h2 id="profile-user-name"><%= user.getFirstName() + " " + user.getLastName() %>
            </h2>
            <div id="profile-age-info">
                <label for="user-age">Возраст:</label>
                <span id="user-age"><%= user.getAge() %></span>
            </div>
            <div id="profile-city-info">
                <label for="user-city">Город:</label>
                <span id="user-city"><%= user.getCity() %></span>
            </div>
            <div id="profile-about-info">
                <label for="user-short-about">О себе:</label>
                <span id="user-short-about"><%= user.getFullAbout() %></span>
            </div>
            <div id="profile-like-profile">
                <div class="user-card-like">
                    <img src="<%= usersIdWhereLiked.contains(user.getId()) ? "../images/active_like.png" : "../images/disactive_like.png" %>"
                         width="20" data-uid="<%= user.getId() %>">
                    <span data-uid="<%= user.getId() %>"><%= user.getCountLikes() %></span>
                </div>
            </div>
        </div>
        <% if(isAuth && !isMyPage) { %>
        <form action="/chat" method="get" style="text-align: center;">
            <input type="hidden" name="toUserId" value="<%= user.getId() %>">
            <input type="submit" class="typical-btn"
                   style="padding:10px; font-family: 'Trebuchet MS', serif; font-size: 1em; width: 100%"
                   value="Написать сообщение">
        </form>
        <% } %>
    </div>
</div>

<div id="toolbar">
    <ul id="available-sections">
        <a href="#thoughts" style="background: rgba(82, 179, 164, 1); text-decoration: underline">
            <li class="thoughts">Мысли</li>
        </a>
        <a href="#liked-users">
            <li class="liked-users">Понравившиеся пользователи</li>
        </a>
    </ul>
</div>

<div id="for-each-user">
    <div id="thoughts">
        <ul>
            <% for (ArticleDto article : userArticles) {%>
            <li>
                <span><%= article.getText() %></span>
                <% if (isMyPage) { %>
                <div style="text-align: end; margin-bottom: 10px; position: absolute; right: 1%; top: 10%; "><img
                        data-id="<%=article.getId()%>" src="../../images/deleteArticle.png" class="delete-article"
                        width="20"></div>
                <% } %>
                <div class="thought-like" data-uid="<%=user.getId()%>" data-aid="<%=article.getId()%>"><img
                        src="<%= articlesIdWhereLiked.contains(article.getId()) ? "../images/active_like.png" : "../images/disactive_like.png" %>"
                        style="<%= !isAuth || request.getAttribute("currentUser") == null ? "cursor: default !important": "" %>"
                        alt=""><span
                        style="<%= !isAuth || request.getAttribute("currentUser") == null ? "cursor: default !important": "" %>"><%= article.getCountLikes() %></span>
                </div>
            </li>
            <% } %>
            <%= userArticles.size() == 0 ? "<li style='text-align: center'>У этого пользователя нет мыслей, а может, и извилин...</li>" : "" %>
        </ul>
    </div>
    <div id="liked-users">
        <ul>
            <% for (String nameLastname : namesWhereLiked) { %>
            <li>
                <h4><span style="font-size: 1.025em"><%= user.getFirstName() + " " + user.getLastName() %>
                </span> проявил<%= user.getGender().equals("Ж") ? "a" : "" %> симпатию к
                    <form action="/profile" method="post" style="display: inline">
                        <input type="hidden" name="user-id" value="<%= nameLastname.split("/")[1] %>">
                        <input type="submit" value="<%= nameLastname.split("/")[0] %>"
                               style="border: none; background: none; outline: none; color: #66B9F9; font-size: 1.1em; font-weight: bold;cursor:pointer;">
                    </form>
                </h4>
            </li>
            <% } %>
            <% if(namesWhereLiked.size() == 0) { %>
                <li>Этому пользователю никто не нравится, скорее всего он любит только себя...</li>
            <% } %>
        </ul>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<script>
    $(document).ready(function () {
        $(".delete-article").click(function () {
            let currentEl = this;
            $.ajax({
                method: "POST",
                data: {deleted: this.dataset.id},
                success: function () {
                    $(currentEl).parent().parent().slideToggle(function () {
                        this.remove()
                    });
                }
            });
        });

        <% if(!isMyPage && isAuth) { %>
        $(".thought-like").click(function () {
            let currentLike = this
            let countLikes = Number($(currentLike).children("span").text())
            $.ajax({
                method: "POST",
                data: {
                    liked: "",
                    article_id: currentLike.dataset.aid,
                    from_user_id: <%= sessionUser.getId() %>,
                    to_user_id: currentLike.dataset.uid,
                    count_likes: countLikes
                },
                success: function () {
                    if ($(currentLike).children("img").attr("src") === "../images/disactive_like.png") {
                        $(currentLike).children("img").attr("src", "../images/active_like.png")
                        $(currentLike).children("span").text(countLikes + 1)
                    } else {
                        $(currentLike).children("img").attr("src", "../images/disactive_like.png")
                        $(currentLike).children("span").text(countLikes - 1)
                    }
                }
            });
        });
        $(".user-card-like").children().click(function () {
            let currentLike = this
            let countLikes = Number($(".user-card-like span").text())
            $.ajax({
                method: "POST",
                data: {
                    likedUser: "",
                    from_user_id: <%= sessionUser.getId() %>,
                    to_user_id: currentLike.dataset.uid,
                    count_likes: countLikes
                },
                success: function () {
                    if ($(".user-card-like img").attr("src") === "../images/disactive_like.png") {
                        $(".user-card-like img").attr("src", "../images/active_like.png")
                        $(".user-card-like span").text(countLikes + 1)
                    } else {
                        $(".user-card-like img").attr("src", "../images/disactive_like.png")
                        $(".user-card-like span").text(countLikes - 1)
                    }
                }
            });
        });
        <% } %>
    });
</script>

</body>
</html>
