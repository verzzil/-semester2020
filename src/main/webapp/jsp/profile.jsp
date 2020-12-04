<%@ page import="ru.itis.dto.UserDto" %>
<%@ page import="ru.itis.dto.ArticleDto" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 30.11.2020
  Time: 3:26
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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><%= user.getFirstName() + " " + user.getLastName() %>
    </title>
    <link rel="stylesheet" href="../css/main.css">
    <link rel="stylesheet" href="../css/profile.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Fredoka+One&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Bungee+Shade&display=swap" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="../js/main.js"></script>
    <script src="../js/submitForms.js"></script>
    <script src="../js/profile.js"></script>

    <style>
        #layerSprite {
            animation: layerAnim 20s ease-in-out infinite;
        }
    </style>

    <% if(isMyPage) {%>
    <style>
        #likeUser, .thoughtsLike {
            cursor: default !important;
        }
    </style>
    <% } %>
</head>
<body>

<jsp:include page="nav.jsp"/>

<div id="profile">
    <div id="user">
        <img src="/user-photo?id=<%= user.getId() %>" alt="">
        <div id="userInfo">
            <h1><%= user.getFirstName() + " " + user.getLastName() %>
            </h1>
            <div id="userInfo2">
                <span>Возраст:</span><span><%= user.getAge() %></span>
                <span>Город:</span><span><%= user.getCity() %></span>
                <span>О себе:</span><span><%= user.getFullAbout() %></span>
            </div>
        </div>
        <% if (isAuth && !isMyPage) { %>
        <form action="/chat" method="post">
            <input type="hidden" name="newDialog" value="<%= user.getId() %>">
            <input type="hidden" name="name" value="<%= user.getFirstName() + " " + user.getLastName() %>">
            <input type="submit" value="Send Message">
        </form>
        <% } %>
        <div id="likeUser" data-uid="<%= user.getId() %>">
            <img src="<%= usersIdWhereLiked.contains(user.getId()) ? "../images/thoughtActiveLike.png" : "../images/thoughtLike.png" %>" width="30" alt="">
            <span><%= user.getCountLikes() %></span>
        </div>
    </div>
    <div id="selection">
        <span id="thoughtsSelected" style="background: #77ffcd;">Thoughts</span>
        <span id="likedUsersSelected">Liked users</span>
    </div>
    <div id="frame">
        <div id="thoughts">
            <ul>
                <% for (ArticleDto article : userArticles) {%>
                <li>
                    <span><%= article.getText() %></span>
                    <div class="actionsBlock">
                        <div class="thoughtsLike" data-uid="<%=user.getId()%>" data-aid="<%=article.getId()%>">
                            <img src="<%= articlesIdWhereLiked.contains(article.getId()) ? "../images/thoughtActiveLike.png" : "../images/thoughtLike.png" %>" width="20" alt="">
                            <span class="countLikesThought"><%= article.getCountLikes() %> likes</span>
                        </div>
                        <% if (isMyPage || (sessionUser != null && sessionUser.isAdmin())) { %>
                        <div class="deleteThought" data-id="<%= article.getId() %>">
                            <img src="../images/deleteThought.png" width="20" alt="">
                        </div>
                        <% } %>
                    </div>
                </li>
                <% } %>
                <%= userArticles.size() == 0 ? "<li style='text-align: center'>У этого пользователя нет мыслей, а может, и извилин...</li>" : "" %>
            </ul>
        </div>
        <div id="likedUsers">
            <ul>
                <% for (String nameLastname : namesWhereLiked) { %>
                <li>
                    <span><%= user.getFirstName() + " " + user.getLastName() %> проявил<%= user.getGender().equals("Ж") ? "a" : "" %> симпатию к
                        <span class="openLikedUser" style="cursor:pointer;color: #5953FF; font-weight: bold">
                            <%= nameLastname.split("/")[0] %>
                            <form action="/profile" method="post" style="display: none">
                                <input type="hidden" name="user-id" value="<%= nameLastname.split("/")[1] %>">
                            </form>
                        </span>
                    </span>

                </li>
                <% } %>
                <% if (namesWhereLiked.size() == 0) { %>
                <li>Этому пользователю никто не нравится, скорее всего он любит только себя...</li>
                <% } %>
            </ul>
        </div>
    </div>
</div>


<script>
    $(document).ready(function () {
        $(".deleteThought").click(function () {
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
        $(".thoughtsLike").click(function () {
            let currentLike = this
            let countLikes = Number($(currentLike).children("span").text().split(" ")[0])
            $.ajax({
                method: "POST",
                data: {
                    liked: "",
                    article_id: currentLike.dataset.aid,
                    from_user_id: <%= sessionUser.getId() %>,
                    to_user_id: currentLike.dataset.uid,
                    count_likes: countLikes
                }
            });
            if ($(currentLike).children("img").attr("src") === "../images/thoughtLike.png") {
                $(currentLike).children("img").attr("src", "../images/thoughtActiveLike.png");
                $(currentLike).children("span").text(countLikes + 1 +" likes");
            } else {
                $(currentLike).children("img").attr("src", "../images/thoughtLike.png");
                $(currentLike).children("span").text(countLikes - 1 + " likes");
            }
        });
        $("#likeUser").click(function () {
            let currentLike = this
            let countLikes = Number($("#likeUser span").text())
            $.ajax({
                method: "POST",
                data: {
                    likedUser: "",
                    from_user_id: <%= sessionUser.getId() %>,
                    to_user_id: currentLike.dataset.uid,
                    count_likes: countLikes
                }
            });
            if ($("#likeUser img").attr("src") === "../images/thoughtLike.png") {
                $("#likeUser img").attr("src", "../images/thoughtActiveLike.png")
                $("#likeUser span").text(countLikes + 1)
            } else {
                $("#likeUser img").attr("src", "../images/thoughtLike.png")
                $("#likeUser span").text(countLikes - 1)
            }
        });
        <% } %>
    });
</script>
</body>
</html>
