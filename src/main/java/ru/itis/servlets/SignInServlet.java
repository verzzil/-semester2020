package ru.itis.servlets;

import ru.itis.dto.SignInForm;
import ru.itis.services.signIn.SignInService;
import ru.itis.services.signUp.SignUpService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {

    private SignInService signInService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        signInService = (SignInService) config.getServletContext().getAttribute("signInService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignInForm form = new SignInForm();

        form.setEmail(req.getParameter("email"));
        form.setPassword(req.getParameter("password"));

        if (signInService.signIn(form)) {
            HttpSession session = req.getSession();
            session.setAttribute("auth", true);
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/signIn");
        }
    }
}
