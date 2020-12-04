package ru.itis.servlets;

import ru.itis.models.FileInfo;
import ru.itis.services.files.FilesService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@WebServlet("/user-photo")
public class UsersPhotoServlet extends HttpServlet {

    private FilesService filesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.filesService = (FilesService) config.getServletContext().getAttribute("filesService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = Integer.parseInt(req.getParameter("id"));
        Optional<FileInfo> fileInfo = filesService.getFileInfo(userId);

        if(fileInfo.isPresent()) {
            resp.setContentType(fileInfo.get().getType());
            resp.setHeader("Content-Disposition", "filename=\"" + fileInfo.get().getOriginalFileName() + "\"");
            filesService.writeFileFromStorage(userId, resp.getOutputStream());
            resp.flushBuffer();
        } else {
            resp.setContentType("image/png");
            File file = new File(
                    "D://Projects/InfaHoWo/src/main/webapp/images/usersPhotos/default.png"
            );
            Files.copy(file.toPath(), resp.getOutputStream());
        }

    }
}
