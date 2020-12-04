package ru.itis.services.signIn;

import ru.itis.dto.SignInForm;
import ru.itis.dto.UserDto;

import java.util.Optional;

public interface SignInService {
    Optional<UserDto> signIn(SignInForm form);
}
