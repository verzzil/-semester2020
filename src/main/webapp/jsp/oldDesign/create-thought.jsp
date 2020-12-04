<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 19.11.2020
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="../../css/common/left-menu.css">
    <link rel="stylesheet" href="../../css/common/main.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="../../js/left-menu.js"></script>
    <style>
        #add-new-thought {
            display: grid;
            gap: 20px;
        }

        #add-new-thought input {
            padding: 10px;
        }
    </style>
</head>
<body>

<jsp:include page="left-menu.jsp"/>
<div style="margin-left: 100px; display: grid;
    grid-template-rows: repeat(3, 1fr);
    padding: 10px;
    justify-content: center;
">
    <h1>Создать мыслю</h1>
    <form id="add-new-thought" method="post" action="/create-thought">
        <textarea name="new-thought" maxlength="400" autofocus
                  placeholder="Свобода мысли начинается здесь(400 символов)" id="new-thought" cols="30"
                  rows="10"></textarea>
        <input type="submit" class="typical-btn" value="Создать">
    </form>
</div>
</body>
</html>
