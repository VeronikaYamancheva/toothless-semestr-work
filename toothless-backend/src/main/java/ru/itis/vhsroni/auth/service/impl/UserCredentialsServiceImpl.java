package ru.itis.vhsroni.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.auth.data.entity.UserCredentials;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.data.enums.AuthProvider;
import ru.itis.vhsroni.auth.data.enums.State;
import ru.itis.vhsroni.auth.data.mapper.UserCredentialsMapper;
import ru.itis.vhsroni.auth.repository.UserCredentialsRepository;
import ru.itis.vhsroni.auth.repository.UserDataRepository;
import ru.itis.vhsroni.auth.service.UserCredentialsService;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.*;
import ru.itis.vhsroni.profile.data.dto.inner.UserCredentialsProfileInfo;
import ru.itis.vhsroni.profile.data.dto.request.UpdatePasswordRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCredentialsServiceImpl implements UserCredentialsService {

    private final UserCredentialsRepository userCredentialsRepository;

    private final UserDataRepository userDataRepository;

    private final UserCredentialsMapper userCredentialsMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ErrorMessageProperties errorMessage;

    @Override
    public void createLocalUserCredentials(UUID userId, String email, String hashedPassword) {
        UserData userData = userDataRepository.findUserDataByUserId(userId)
                .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        UserCredentials userCredentials = userCredentialsMapper.toLocalUserCredentials(userData, email, hashedPassword);
        userCredentialsRepository.save(userCredentials);
    }

    @Override
    public void createOAuthUserCredentials(UUID userId, String email, AuthProvider authProvider) {
        UserData userData = userDataRepository.findUserDataByUserId(userId)
                .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        UserCredentials userCredentials = UserCredentials.builder()
                .providerType(authProvider)
                .userData(userData)
                .providerKey(email)
                .build();
        userCredentialsRepository.save(userCredentials);
    }

    @Override
    public boolean checkLoginLocalCredentials(String email, String password) {
        Optional<UserCredentials> userCredentials = userCredentialsRepository.findActiveByProviderKeyAndProviderType(
                email, AuthProvider.LOCAL);
        if (userCredentials.isPresent()) {
            System.out.println(bCryptPasswordEncoder.encode(password));
            String hashedPassword = userCredentials.get().getHashPassword();
            UserData userData = userCredentials.get().getUserData();
            if (userData.getState() == State.NOT_VERIFIED) {
                throw new ForbiddenException(errorMessage.accountNotVerified(), ErrorCode.ACCOUNT_NOT_VERIFIED);
            }
            if (userData.getState() == State.BANNED) {
                throw new ForbiddenException(errorMessage.accountNotVerified(), ErrorCode.ACCOUNT_BANNED);
            }
            return bCryptPasswordEncoder.matches(password, hashedPassword);
        }
        return false;
    }

    @Override
    public UUID credentialsExist(String providerKey, AuthProvider authProvider) {
        Optional<UserCredentials> userCredentials = userCredentialsRepository.findActiveByProviderKeyAndProviderType(providerKey, authProvider);
        return userCredentials.map(credentials -> credentials.getUserData().getUserId()).orElse(null);
    }

    @Override
    public void updateLocalPassword(UUID userId, UpdatePasswordRequest updatePasswordRequest) {
        String newPassword = updatePasswordRequest.newPassword();
        String confirmPassword = updatePasswordRequest.newConfirmPassword();
        if (!newPassword.equals(confirmPassword)) {
            throw new BadRequestException(errorMessage.passwordsDoNotMatch(), ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }
        List<UserCredentials> userCredentials = userCredentialsRepository.findAllByUserData_UserId(userId);
        boolean wasUpdated = false;
        for (UserCredentials cred : userCredentials) {
            if (cred.getProviderType() == AuthProvider.LOCAL) {
                System.out.println("LOCAL");
                if (bCryptPasswordEncoder.matches(updatePasswordRequest.oldPassword(), cred.getHashPassword())){
                    cred.setHashPassword(bCryptPasswordEncoder.encode(newPassword));
                    userCredentialsRepository.save(cred);
                    wasUpdated = true;
                } else {
                    throw new BadRequestException(errorMessage.passwordsDoNotMatch(), ErrorCode.PASSWORDS_DO_NOT_MATCH);
                }
            }
        }
        if (!wasUpdated) {
            UserData userData = userDataRepository.findUserDataByUserId(userId)
                    .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
            UserCredentials newUserCredentials = UserCredentials.builder()
                    .userData(userData)
                    .providerType(AuthProvider.LOCAL)
                    .providerKey(userData.getEmail())
                    .hashPassword(bCryptPasswordEncoder.encode(newPassword))
                    .build();
            userCredentialsRepository.save(newUserCredentials);
        }
    }

    @Override
    public UserCredentialsProfileInfo getUserCredentialsProfileInfo(UUID userId) {
        List<UserCredentials> userCredentials = userCredentialsRepository.findAllByUserData_UserId(userId);
        List<UserCredentialsProfileInfo.AuthProviderResponse> authProviderResponses = userCredentials.stream()
                .map(cred -> UserCredentialsProfileInfo.AuthProviderResponse.builder()
                        .authProvider(cred.getProviderType())
                        .providerKey(cred.getProviderKey())
                        .build())
                .toList();
        return UserCredentialsProfileInfo.builder().authProviders(authProviderResponses).build();
    }
}
