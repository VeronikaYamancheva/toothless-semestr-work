package ru.itis.vhsroni.auth.service;

import ru.itis.vhsroni.auth.data.dto.form.ClientRegistrationForm;
import ru.itis.vhsroni.auth.data.dto.form.DentistRegistrationForm;
import ru.itis.vhsroni.auth.data.dto.form.LoginForm;
import ru.itis.vhsroni.auth.data.dto.response.AccessTokenResponse;
import ru.itis.vhsroni.auth.data.dto.inner.JwtTokensResponse;

import java.util.UUID;

public interface AuthenticationService {

    UUID registerClient(ClientRegistrationForm clientRegistrationForm);

    UUID registerDentist(DentistRegistrationForm dentistRegistrationForm);

    void verify(String email, String code);

    JwtTokensResponse login(LoginForm loginForm);

    void logout(UUID userId);

    AccessTokenResponse refresh(String refreshToken);

    boolean checkEmailIsAvailable (String email);
}
