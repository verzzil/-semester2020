<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 30.11.2020
  Time: 5:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create thought</title>
    <link rel="stylesheet" href="../css/main.css">
    <link rel="stylesheet" href="../css/createThought.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Fredoka+One&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Bungee+Shade&display=swap" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="../js/main.js"></script>
    <script src="../js/submitForms.js"></script>
    <style>
        #layerSprite {
            animation: layerAnim 20s ease-in-out infinite;
        }
    </style>
</head>
<body>

<jsp:include page="nav.jsp" />

<h1>Create thought</h1>
<form id="createThought" method="post" action="/create-thought">
        <textarea name="new-thought" maxlength="400" autofocus
                  placeholder="Свобода мысли начинается здесь(400 символов)" id="thoughtText" cols="30"
                  rows="10"></textarea>
    <input type="submit" value="Create">
</form>

<jsp:include page="layerSprite.jsp" />


</body>
</html>
