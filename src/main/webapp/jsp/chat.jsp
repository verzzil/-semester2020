<%@ page import="ru.itis.dto.UserDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="ru.itis.dto.Message" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="ru.itis.dto.coders.MessageSerializer" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 30.11.2020
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder
            .registerTypeAdapter(UserDto.class, new MessageSerializer())
            .create();
    List<UserDto> chattingUsers = (List<UserDto>) request.getAttribute("chattingUsers");
    HashMap<Integer, List<Message>> dialogs = (HashMap<Integer, List<Message>>) request.getAttribute("dialogs");
    String jsonDialogs = gson.toJson(dialogs);
    String fromUserId = ((UserDto) request.getSession().getAttribute("user")).getId().toString();

%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Messages</title>
    <link rel="stylesheet" href="../css/main.css">
    <link rel="stylesheet" href="../css/chat.css">
    <link href="https://fonts.googleapis.com/css2?family=Fredoka+One&display=swap" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="../js/main.js"></script>
    <script src="../js/submitForms.js"></script>
</head>
<body>

<div id="plug"></div>

<jsp:include page="nav.jsp"/>

<h1 style="justify-self: center; font-family: 'Fredoka One', cursive; color: #fff;">Messages</h1>
<div id="messages">
    <div id="messageList">
        <ul>
            <% for (UserDto user : chattingUsers) {
                String lastMessage = dialogs.get(user.getId()).get(dialogs.get(user.getId()).size() - 1).getText();%>
            <li data-uid="<%= user.getId() %>">
                <img src="/user-photo?id=<%= user.getId() %>" alt="">
                <h3><%= user.getFirstName() + " " + user.getLastName() %>
                </h3>
                <span><%= dialogs.get(user.getId()).get(dialogs.get(user.getId()).size() - 1).getFromUserId() == user.getId() ? "" : "Вы: " %>
                    <%= lastMessage.length() > 70 ? lastMessage.substring(0, 70) + "..." : lastMessage %>
                </span>
            </li>
            <% } %>
        </ul>
    </div>
    <div id="messageHistory" data-uid="containDialogUserId" style="display:none;">
        <div id="history">
            <%--            message container    --%>
        </div>
        <div id="sendMessage">
            <textarea autofocus name="" id="messageText"></textarea>
            <button id="submitBtn">Send</button>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {

        let jsonDialogs = <%= jsonDialogs %>;

        let chatUnit = {
            init() {
                this.msgTextArea = document.getElementById("messageText");
                this.submitBtn = document.getElementById("submitBtn");
                this.msgBlock = document.getElementById("history");

                this.bindEvents();
            },
            bindEvents() {
                this.openSocket();
                this.submitBtn.addEventListener("click", e => {
                    if (this.msgTextArea.value.length !== 0)
                        console.log("sendEvent");
                    this.send(Number($("#messageHistory")[0].dataset.uid));
                });
            },
            send(toUserId) {
                this.sendMessage({
                    fromUserId: <%= fromUserId %>,
                    toUserId: toUserId,
                    text: this.msgTextArea.value.trim()
                });
                $.ajax({
                    method: "POST",
                    data: {msg: "", fromId: <%= fromUserId %>, toId: toUserId, text: $("#messageText").val()},
                    success: function () {
                        $("#messageText").val("")
                    }
                });
            },
            onOpenSock() {
            },
            onMessage(msg) {
                let msgHtml;
                if (msg.fromUserId === <%= fromUserId %>) {
                    msgHtml = "<div class=\"myMessage\"><span>" + msg.text + "</span></div>";
                    jsonDialogs[msg.toUserId].push(msg);

                    updateUserList(true, msg.toUserId, false, msg.text);

                } else if (msg.fromUserId === Number($("#messageHistory")[0].dataset.uid)) {
                    msgHtml = "<div class=\"friendMessage\"><span>" + msg.text + "</span></div>";
                    jsonDialogs[msg.fromUserId].push(msg);

                    updateUserList(false, msg.fromUserId, true, msg.text);

                } else {
                    if (!$("*").is($("#messageList ul li[data-uid=" + msg.fromUserId + "]"))) {
                        jsonDialogs[msg.fromUserId] = [];
                    }
                    jsonDialogs[msg.fromUserId].push(msg);

                    updateUserList(false, msg.fromUserId, false, msg.text);

                    return;
                }

                this.msgBlock.insertAdjacentHTML('beforeend', msgHtml);

                this.msgBlock.scrollTop = this.msgBlock.scrollHeight;
            },
            onClose() {
            },
            sendMessage(msg) {
                this.ws.send(JSON.stringify(msg));
            },
            openSocket() {
                this.ws = new WebSocket("ws://localhost:8080/chat/" +<%= fromUserId %>);
                this.ws.onopen = () => this.onOpenSock();
                this.ws.onmessage = (e) => this.onMessage(JSON.parse(e.data));
                this.ws.onclose = () => this.onClose();
            },
        };

        $("#messageList ul").on('click', 'li', function () {
            $("#messageList ul li").removeClass("activeDialog");
            $(this).addClass("activeDialog");
            let withUserId = this.dataset.uid;

            showHistory(jsonDialogs, withUserId);

        });

        chatUnit.init();

        <% if(request.getParameter("newDialog") != null) {
            Integer withUserId = Integer.parseInt(request.getParameter("newDialog")); %>
        if (Number(<%= withUserId %>) in jsonDialogs) {
            $("#messageList ul li[data-uid=\"<%= withUserId %>\"]").addClass("activeDialog");

            showHistory(jsonDialogs, <%= withUserId %>);
        } else {
            jsonDialogs[<%= withUserId %>] = [];
            let html = "<li class=\"activeDialog\" data-uid=\"<%= request.getParameter("newDialog") %>\"><img src=\"/user-photo?id=<%= request.getParameter("newDialog") %>\"><h3><%= request.getParameter("name") %></h3><span></span></li>";
            $("#messageHistory").css('display', 'grid');
            $("#messageHistory")[0].dataset.uid = <%= withUserId %>;
            $("#messageList ul").prepend(html);
        }
        <% } %>
    });

    function updateUserList(isMyDialog, userId, isActive, msg) {
        let liUser;
        let userName;
        if (isMyDialog) {
            userName = $("li[data-uid=" + userId + "] h3").text();

            liUser = "<li class=\"activeDialog\" data-uid=" + userId + "><img src=\"/user-photo?id=" + userId + "\"><h3>" + userName + "</h3><span>Вы: " + msg + "</span></li>";
        } else {
            userName = $("li[data-uid=" + userId + "] h3").text();
            if (isActive)
                liUser = "<li class=\"activeDialog\" data-uid=" + userId + "><img src=\"/user-photo?id=" + userId + "\"><h3>" + userName + "</h3><span>" + msg + "</span></li>";
            else {
                if(userName === "") {
                    $.ajax({
                        method: "POST",
                        async: false,
                        data: {getNameByUserId: userId},
                        success: function(data) {
                            liUser = "<li data-uid=" + userId + "><img src=\"/user-photo?id=" + userId + "\"><h3>" + data.replace("\"","") + "</h3><span>" + msg + "</span></li>";
                        }
                    });
                } else
                    liUser = "<li data-uid=" + userId + "><img src=\"/user-photo?id=" + userId + "\"><h3>" + userName + "</h3><span>" + msg + "</span></li>";
            }
        }

        $("li[data-uid=" + userId + "]").remove();
        $("#messageList ul").prepend(liUser);
    }

    function showHistory(jsonDialogs, withUserId) {
        $("#messageHistory").css('display', 'grid');

        if ($("#messageHistory")[0].dataset.uid !== withUserId) {
            $("#history").empty();
            $("#messageHistory")[0].dataset.uid = withUserId;
            for (let i in jsonDialogs[withUserId]) {
                if (jsonDialogs[withUserId][i].fromUserId === <%= Integer.parseInt(fromUserId) %>) {
                    $("#history").append("<div class=\"myMessage\"><span>" + jsonDialogs[withUserId][i].text + "</span></div>");
                } else {
                    $("#history").append("<div class=\"friendMessage\"><span>" + jsonDialogs[withUserId][i].text + "</span></div>");
                }
            }
        }
        $("#history").scrollTop($("#history").prop('scrollHeight'));
    }
</script>

</body>
</html>