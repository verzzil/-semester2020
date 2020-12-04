package ru.itis.servlets;

import ru.itis.dto.UserDto;
import ru.itis.models.Article;
import ru.itis.services.article.ArticleService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/create-thought")
public class CreateThoughtServlet extends HttpServlet {

    private ArticleService articleService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        articleService = (ArticleService) config.getServletContext().getAttribute("articleService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("user") == null)
            resp.sendRedirect("/");

        req.getRequestDispatcher("/jsp/createThought.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Article article = Article.builder()
                .text(req.getParameter("new-thought"))
                .countLikes(0)
                .userId(((UserDto)req.getSession().getAttribute("user")).getId())
                .build();

        articleService.save(article);

        resp.sendRedirect("/create-thought");
    }
}
