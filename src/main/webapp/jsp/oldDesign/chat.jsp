<%@ page import="ru.itis.dto.UserDto" %>
<%@ page import="ru.itis.dto.Message" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 24.11.2020
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%
    String fromUserId = ((UserDto) request.getSession().getAttribute("user")).getId().toString();
    UserDto toUser = (UserDto) request.getAttribute("user");
    String toUserId = toUser.getId().toString();
    List<Message> messageHistory = (List<Message>) request.getAttribute("messageHistory");
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Chat</title>
    <link rel="stylesheet" href="../../css/common/main.css">
    <link rel="stylesheet" href="../../css/common/left-menu.css">
    <link rel="stylesheet" href="../../css/chat/main.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="../../js/left-menu.js"></script>
</head>
<body>

<jsp:include page="left-menu.jsp"/>

<div id="chat">
    <div id="chat-user-info">
        <h2><%= toUser.getFirstName() + " " + toUser.getLastName() %>
        </h2>
    </div>
    <div id="messages">
        <% for (Message msg : messageHistory) { %>
            <% if (msg.getFromUserId().toString().equals(fromUserId)) { %>
                <div class="my-msg"><span><%= msg.getText() %></span></div>
            <% } else { %>
                <div class="friend-msg"><span><%= msg.getText() %></span></div>
            <% } %>
        <% } %>
    </div>
    <div id="send-message">
        <textarea name="" id="messageText"></textarea>
        <button class="typical-btn" style="margin: 0 10px" id="submitBtn">Send</button>
    </div>
</div>

<script>
    let chatUnit = {
        init() {
            this.chatbox = document.getElementById("chat");

            this.msgTextArea = document.getElementById("messageText");
            this.submitBtn = document.getElementById("submitBtn");
            this.msgBlock = document.getElementById("messages");

            this.bindEvents();
        },
        bindEvents() {
            this.openSocket();
            this.submitBtn.addEventListener("click", e => {
                if (this.msgTextArea.value.length !== 0)
                    this.send();
            });
        },
        send() {
            this.sendMessage({
                fromUserId: <%= fromUserId %>,
                toUserId: <%= toUserId %>,
                text: this.msgTextArea.value.trim()
            });
        },
        onOpenSock() {

        },
        onMessage(msg) {
            let msgHtml;
            if (msg.fromUserId === <%= fromUserId %>) {
                msgHtml = "<div class=\"my-msg\"><span>" + msg.text + "</span></div>";
            } else if (msg.fromUserId === <%= toUserId %>) {
                msgHtml = "<div class=\"friend-msg\"><span>" + msg.text + "</span></div>";
            } else return;

            this.msgBlock.insertAdjacentHTML('beforeend', msgHtml);

            this.msgBlock.scrollTop = this.msgBlock.scrollHeight;
        },
        onClose() {

        },
        sendMessage(msg) {
            this.ws.send(JSON.stringify(msg));
        },
        openSocket() {
            this.ws = new WebSocket("ws://localhost:8080/chat/" +<%=fromUserId%>);
            this.ws.onopen = () => this.onOpenSock();
            this.ws.onmessage = (e) => this.onMessage(JSON.parse(e.data));
            this.ws.onclose = () => this.onClose();
        },
    };

    window.addEventListener("load", e => chatUnit.init());

    $(document).ready(function () {

        $("#messages").scrollTop( $("#messages").prop("scrollHeight") );

        $("#submitBtn").click(function () {
            if ($("#messageText").val().trim() !== "") {
                $.ajax({
                    method: "POST",
                    data: {fromId: <%= fromUserId %>, toId: <%= toUserId %>, text: $("#messageText").val()},
                    success: function () {
                        $("#messageText").val("")
                    }
                });
            }
        });
    });

</script>

</body>
</html>
