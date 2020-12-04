package ru.itis.servlets;

import ru.itis.dto.SignUpForm;
import ru.itis.services.signUp.SignUpService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private SignUpService signUpService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        signUpService = (SignUpService) config.getServletContext().getAttribute("signUpService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("user") != null) {
            resp.sendRedirect("/");
            return;
        }
        req.getRequestDispatcher("/jsp/signUp.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignUpForm form = new SignUpForm();

        form.setFirstName(req.getParameter("firstName"));
        form.setLastName(req.getParameter("lastName"));
        form.setEmail(req.getParameter("email"));
        form.setPassword(req.getParameter("password"));
        form.setGender(req.getParameter("gender"));
        form.setCity(req.getParameter("city"));
        form.setAge(Integer.parseInt(req.getParameter("age")));

        signUpService.signUp(form);

        resp.sendRedirect("/");
    }
}
