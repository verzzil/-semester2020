package ru.itis.listeners;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.repositories.articles.ArticlesRepository;
import ru.itis.repositories.articles.ArticlesRepositoryImpl;
import ru.itis.repositories.files.FilesRepository;
import ru.itis.repositories.files.FilesRepositoryImpl;
import ru.itis.repositories.messages.MessagesRepository;
import ru.itis.repositories.messages.MessagesRepositoryImpl;
import ru.itis.repositories.users.UsersRepository;
import ru.itis.repositories.users.UsersRepositoryImpl;
import ru.itis.services.article.ArticleService;
import ru.itis.services.article.ArticleServiceImpl;
import ru.itis.services.files.FilesService;
import ru.itis.services.files.FilesServiceImpl;
import ru.itis.services.messages.MessagesService;
import ru.itis.services.messages.MessagesServiceImpl;
import ru.itis.services.signIn.SignInService;
import ru.itis.services.signIn.SignInServiceImpl;
import ru.itis.services.signUp.SignUpService;
import ru.itis.services.signUp.SignUpServiceImpl;
import ru.itis.services.users.UsersService;
import ru.itis.services.users.UsersServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 23.10.2020
 * 4. Simple Web Application
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@WebListener
public class CustomServletContextListener implements ServletContextListener {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "80pufuda";
    private static final String DB_DRIVER = "org.postgresql.Driver";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        ArticlesRepository articlesRepository = new ArticlesRepositoryImpl(dataSource);
        UsersRepository usersRepository = new UsersRepositoryImpl(dataSource);
        FilesRepository filesRepository = new FilesRepositoryImpl(dataSource);
        MessagesRepository messagesRepository = new MessagesRepositoryImpl(dataSource);

        ArticleService articleService = new ArticleServiceImpl(articlesRepository);
        SignUpService signUpService = new SignUpServiceImpl(usersRepository);
        SignInService signInService = new SignInServiceImpl(usersRepository);
        UsersService usersService = new UsersServiceImpl(usersRepository);
        FilesService filesService = new FilesServiceImpl(filesRepository);
        MessagesService messagesService = new MessagesServiceImpl(messagesRepository);

        servletContext.setAttribute("signUpService", signUpService);
        servletContext.setAttribute("signInService", signInService);
        servletContext.setAttribute("usersService", usersService);
        servletContext.setAttribute("articleService", articleService);
        servletContext.setAttribute("filesService", filesService);
        servletContext.setAttribute("messagesService", messagesService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
