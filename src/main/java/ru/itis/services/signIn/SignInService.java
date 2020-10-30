package ru.itis.services.signIn;

import ru.itis.dto.SignInForm;

public interface SignInService {
    boolean signIn(SignInForm form);
}
