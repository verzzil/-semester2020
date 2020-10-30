package ru.itis.listeners;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.repositories.UsersRepository;
import ru.itis.repositories.UsersRepositoryImpl;
import ru.itis.services.signIn.SignInService;
import ru.itis.services.signIn.SignInServiceImpl;
import ru.itis.services.signUp.SignUpService;
import ru.itis.services.signUp.SignUpServiceImpl;

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

        UsersRepository usersRepository = new UsersRepositoryImpl(dataSource);
        SignUpService signUpService = new SignUpServiceImpl(usersRepository);
        SignInService signInService = new SignInServiceImpl(usersRepository);
        servletContext.setAttribute("signUpService", signUpService);
        servletContext.setAttribute("signInService", signInService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
