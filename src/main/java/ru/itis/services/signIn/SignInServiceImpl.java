package ru.itis.services.signIn;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.dto.SignInForm;
import ru.itis.dto.UserDto;
import ru.itis.models.User;
import ru.itis.repositories.users.UsersRepository;

import java.util.Optional;

public class SignInServiceImpl implements SignInService {

    private UsersRepository usersRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public SignInServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public Optional<UserDto> signIn(SignInForm form) {
        Optional<User> user = usersRepository.exitUser(form.getEmail());

        if(user.isPresent())
            if(passwordEncoder.matches(form.getPassword(), user.get().getHashPassword()))
                return Optional.ofNullable(user.get().toUserDto());

        return Optional.empty();
    }
}
