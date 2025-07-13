package ru.itis.vhsroni.auth.service;

import ru.itis.vhsroni.auth.data.enums.AuthProvider;
import ru.itis.vhsroni.profile.data.dto.inner.UserCredentialsProfileInfo;
import ru.itis.vhsroni.profile.data.dto.request.UpdatePasswordRequest;

import java.util.UUID;

public interface UserCredentialsService {

    void createLocalUserCredentials(UUID userId, String email, String hashedPassword);

    void createOAuthUserCredentials(UUID userId, String email, AuthProvider authProvider);

    boolean checkLoginLocalCredentials(String email, String password);

    UUID credentialsExist(String providerKey, AuthProvider authProvider);

    void updateLocalPassword(UUID userId, UpdatePasswordRequest updatePasswordRequest);

    UserCredentialsProfileInfo getUserCredentialsProfileInfo(UUID userId);
}
