package ru.itis.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.itis.dto.SignInForm;
import ru.itis.dto.UserDto;
import ru.itis.dto.coders.UserDtoSerializer;
import ru.itis.services.users.UsersService;
import ru.itis.services.signIn.SignInService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("")
public class MainServlet extends HttpServlet {

    private SignInService signInService;
    private UsersService usersService;
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(UserDto.class, new UserDtoSerializer())
            .create();

    @Override
    public void init(ServletConfig config) throws ServletException {
        signInService = (SignInService) config.getServletContext().getAttribute("signInService");
        usersService = (UsersService) config.getServletContext().getAttribute("usersService");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDto> users = usersService.getAll();
        req.setAttribute("users", users);

        if (req.getSession().getAttribute("user") != null)
            req.setAttribute("likedUsers", usersService.likedUsers(((UserDto) req.getSession().getAttribute("user")).getId()));
        else
            req.setAttribute("likedUsers", new ArrayList<Integer>());

        req.setAttribute("mainPage", "");

        req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignInForm form = new SignInForm();

        if (req.getParameter("sign-in") != null) {
            form.setEmail(req.getParameter("email"));
            form.setPassword(req.getParameter("password"));

            Optional<UserDto> user = signInService.signIn(form);

            if (user.isPresent()) {
                HttpSession session = req.getSession();
                session.setAttribute("auth", true);
                session.setAttribute("user", user.get());
            }
            resp.sendRedirect("/");
        } else if (req.getParameter("logout") != null) {
            req.getSession().removeAttribute("auth");
            req.getSession().removeAttribute("user");
            resp.sendRedirect("/");
        } else if (req.getParameter("liked") != null) {
            usersService.like(
                    Integer.parseInt(req.getParameter("from_user_id")),
                    Integer.parseInt(req.getParameter("to_user_id")),
                    Integer.parseInt(req.getParameter("count_likes"))
            );
        } else if (req.getParameter("filter") != null) {
            String gender = req.getParameter("gender"),
                    fromAge = req.getParameter("from-age").equals("") ? "0" : req.getParameter("from-age"),
                    toAge = req.getParameter("to-age").equals("") ? "10000000" : req.getParameter("to-age"),
                    city = req.getParameter("city");
            Optional<List<UserDto>> filterUsers = usersService.findUsersByFilter(gender, fromAge, toAge, city);

            if (filterUsers.isPresent()) {
                req.setAttribute("users", filterUsers.get());
            } else {
                req.setAttribute("users", new ArrayList<UserDto>());
            }

            req.setAttribute("mainPage", "");

            if (req.getSession().getAttribute("user") != null)
                req.setAttribute("likedUsers", usersService.likedUsers(((UserDto) req.getSession().getAttribute("user")).getId()));
            else
                req.setAttribute("likedUsers", new ArrayList<Integer>());

            req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
        }

        if (req.getSession().getAttribute("user") != null)
            req.setAttribute("likedUsers", gson.toJson(usersService.likedUsers(((UserDto) req.getSession().getAttribute("user")).getId())));
        else
            req.setAttribute("likedUsers", gson.toJson(new ArrayList<Integer>()));

    }
}
