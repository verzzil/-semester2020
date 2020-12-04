package ru.itis.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import ru.itis.dto.Message;
import ru.itis.dto.UserDto;
import ru.itis.services.messages.MessagesService;
import ru.itis.services.users.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    private MessagesService messagesService;
    private UsersService usersService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        messagesService = (MessagesService) config.getServletContext().getAttribute("messagesService");
        usersService = (UsersService) config.getServletContext().getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("/");
            return;
        }

        UserDto currentUser = (UserDto) req.getSession().getAttribute("user");

        Optional<List<UserDto>> chattingUsers = usersService.getChattingUsers(currentUser.getId());

        if (chattingUsers.isPresent()) {
            HashMap<Integer, List<Message>> dialogs = messagesService.getDialogs(
                    currentUser.getId(),
                    chattingUsers.get()
            );
            req.setAttribute("chattingUsers", chattingUsers.get());
            req.setAttribute("dialogs", dialogs);
        } else
            req.setAttribute("chattingUsers", new ArrayList<>());

        req.getRequestDispatcher("/jsp/chat.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getParameter("msg") != null) {
            Integer fromId = Integer.parseInt(req.getParameter("fromId"));
            Integer toId = Integer.parseInt(req.getParameter("toId"));
            String text = req.getParameter("text");

            Message message = Message.builder()
                    .fromUserId(fromId)
                    .toUserId(toId)
                    .text(text)
                    .build();

            messagesService.saveToDb(message);

        } else if(req.getParameter("newDialog") != null) {
            UserDto currentUser = (UserDto) req.getSession().getAttribute("user");

            Optional<List<UserDto>> chattingUsers = usersService.getChattingUsers(currentUser.getId());

            if (chattingUsers.isPresent()) {
                HashMap<Integer, List<Message>> dialogs = messagesService.getDialogs(
                        currentUser.getId(),
                        chattingUsers.get()
                );
                req.setAttribute("chattingUsers", chattingUsers.get());
                req.setAttribute("dialogs", dialogs);
            } else {
                req.setAttribute("chattingUsers", new ArrayList<>());
                req.setAttribute("dialogs", new HashMap<Integer, List<Message>>());
            }

            req.getRequestDispatcher("/jsp/chat.jsp").forward(req,resp);
        } else if(req.getParameter("getNameByUserId") != null) {
            Gson gson = new Gson();
            String element = gson.toJson(usersService.getNameByUserId(Integer.parseInt(req.getParameter("getNameByUserId"))));
            PrintWriter writer = resp.getWriter();
            writer.write(element);
        }

    }
}
