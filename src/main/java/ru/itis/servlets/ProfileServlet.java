package ru.itis.servlets;

import ru.itis.dto.ArticleDto;
import ru.itis.dto.UserDto;
import ru.itis.services.article.ArticleService;
import ru.itis.services.users.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private UsersService usersService;
    private ArticleService articleService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersService = (UsersService) config.getServletContext().getAttribute("usersService");
        articleService = (ArticleService) config.getServletContext().getAttribute("articleService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<List<ArticleDto>> currentUserArticles;
        Optional<List<String>> namesWhereLiked;

        if(req.getParameter("liked") != null) {
            articleService.like(
                    Integer.parseInt(req.getParameter("article_id")),
                    Integer.parseInt(req.getParameter("from_user_id")),
                    Integer.parseInt(req.getParameter("to_user_id")),
                    Integer.parseInt(req.getParameter("count_likes"))
            );
        }
        else if(req.getParameter("likedUser") != null) {
            usersService.like(
                    Integer.parseInt(req.getParameter("from_user_id")),
                    Integer.parseInt(req.getParameter("to_user_id")),
                    Integer.parseInt(req.getParameter("count_likes"))
            );
        }

        if (req.getParameter("deleted") != null) {
            articleService.delete(Integer.parseInt(req.getParameter("deleted")));
        }

        if (req.getParameter("user-id") != null) {
            Integer currentUserId = Integer.parseInt(req.getParameter("user-id"));
            Optional<UserDto> currentUser = usersService.findById(currentUserId);

            currentUserArticles = articleService.showUserArticles(currentUserId);
            namesWhereLiked = usersService.getNamesWhereLiked(currentUserId);

            currentUser.ifPresent(userDto -> req.setAttribute("currentUser", userDto));



        } else {
            currentUserArticles = articleService.showUserArticles(
                    ((UserDto) req.getSession().getAttribute("user")).getId()
            );
            namesWhereLiked = usersService.getNamesWhereLiked(
                    ((UserDto) req.getSession().getAttribute("user")).getId()
            );
        }

        if (currentUserArticles.isPresent()) {
            req.setAttribute("currentUserArticles", currentUserArticles.get());
        } else {
            req.setAttribute("currentUserArticles", new ArrayList<ArticleDto>());
        }
        if (namesWhereLiked.isPresent()) {
            req.setAttribute("namesWhereLiked", namesWhereLiked.get());
        } else {
            req.setAttribute("namesWhereLiked", new ArrayList<ArticleDto>());
        }


        if(req.getSession().getAttribute("user") != null) {
            UserDto curUsr = usersService.findById(((UserDto)req.getSession().getAttribute("user")).getId()).get();
            req.getSession().setAttribute("user", curUsr);

            req.setAttribute("likedArticles", articleService.findAllIdsWhereLiked(((UserDto) req.getSession().getAttribute("user")).getId()));
            req.setAttribute("likedUsers", usersService.likedUsers(((UserDto) req.getSession().getAttribute("user")).getId()));
        }
        else {
            req.setAttribute("likedArticles", new ArrayList<Integer>());
            req.setAttribute("likedUsers", new ArrayList<Integer>());
        }


        req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
    }
}
