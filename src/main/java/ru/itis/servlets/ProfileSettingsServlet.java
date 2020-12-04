package ru.itis.servlets;

import ru.itis.dto.UserDto;
import ru.itis.services.files.FilesService;
import ru.itis.services.users.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/settings")
@MultipartConfig
public class ProfileSettingsServlet extends HttpServlet {

    private UsersService usersService;
    private FilesService filesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersService = (UsersService) config.getServletContext().getAttribute("usersService");
        filesService = (FilesService) config.getServletContext().getAttribute("filesService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("/");
            return;
        }
        req.getRequestDispatcher("/jsp/settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDto updateSessionUser = (UserDto) (req.getSession().getAttribute("user"));


        if (req.getParameter("name-lastname-city") != null) {
            String name = req.getParameter("name").equals("") ? updateSessionUser.getFirstName() : req.getParameter("name"),
                    lastname = req.getParameter("lastname").equals("") ? updateSessionUser.getLastName() : req.getParameter("lastname"),
                    city = req.getParameter("city").equals("") ? updateSessionUser.getCity() : req.getParameter("city");

            updateSessionUser.setFirstName(name);
            updateSessionUser.setLastName(lastname);
            updateSessionUser.setCity(city);

            usersService.updateFirstAndLastNameAndCity(name, lastname, city, updateSessionUser.getId());

        } else if (req.getParameter("change-pass") != null) {
            String oldPas = req.getParameter("old-password"), newPas = req.getParameter("new-password"), repeatPas = req.getParameter("repeat-new-password");

            if(usersService.checkPass(updateSessionUser.getId(), oldPas) && newPas.equals(repeatPas)) {
                usersService.updatePassword(newPas, updateSessionUser.getId());
            }

        } else if (req.getParameter("shortAbout") != null) {
            String shortAbout = req.getParameter("shortAbout").equals("") ? updateSessionUser.getShortAbout() :  req.getParameter("shortAbout"),
                    fullAbout = req.getParameter("fullAbout").equals("") ? updateSessionUser.getFullAbout() : req.getParameter("fullAbout");

            updateSessionUser.setShortAbout(shortAbout);
            updateSessionUser.setFullAbout(fullAbout);

            usersService.updateShortAndFullAbout(updateSessionUser.getId(), shortAbout, fullAbout);

        } else {
            Part part = req.getPart("file");

            filesService.saveFileToStorage(
                    part.getInputStream(),
                    updateSessionUser.getId(),
                    part.getSubmittedFileName(),
                    part.getContentType()
            );

        }

        req.getSession().setAttribute("user", updateSessionUser);

        resp.sendRedirect("/settings");

    }
}
