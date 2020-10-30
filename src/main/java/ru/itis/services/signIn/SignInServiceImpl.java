package ru.itis.services.signIn;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.dto.SignInForm;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SignInServiceImpl implements SignInService {

    private UsersRepository usersRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public SignInServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public boolean signIn(SignInForm form) {

        Optional<User> user = usersRepository.findUserByEmail(form.getEmail());

        if(user.isPresent()) {
            if(passwordEncoder.matches(form.getPassword(), user.get().getHashPassword()))
                return true;
        }
        return false;

    }
}
