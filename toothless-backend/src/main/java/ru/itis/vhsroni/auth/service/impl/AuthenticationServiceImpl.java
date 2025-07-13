package ru.itis.vhsroni.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.auth.data.dto.form.BaseRegistrationForm;
import ru.itis.vhsroni.auth.data.dto.form.ClientRegistrationForm;
import ru.itis.vhsroni.auth.data.dto.form.DentistRegistrationForm;
import ru.itis.vhsroni.auth.data.dto.form.LoginForm;
import ru.itis.vhsroni.auth.data.dto.response.AccessTokenResponse;
import ru.itis.vhsroni.auth.data.dto.inner.JwtTokensResponse;
import ru.itis.vhsroni.auth.jwt.JwtProvider;
import ru.itis.vhsroni.auth.service.*;
import ru.itis.vhsroni.auth.util.validator.validator.RegistrationFormValidator;
import ru.itis.vhsroni.client.service.ClientService;
import ru.itis.vhsroni.config.property.AuthConfigProperties;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.UnauthorizedException;
import ru.itis.vhsroni.error.exception.BadRequestException;
import ru.itis.vhsroni.error.exception.ForbiddenException;
import ru.itis.vhsroni.util.mail.MailService;

import java.security.SecureRandom;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ClientService clientService;

    private final RefreshTokenStoreService refreshTokenStoreService;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final MailService mailService;

    private final RegistrationFormValidator registrationFormValidator;

    private final UserDataService userDataService;

    private final UserCredentialsService userCredentialsService;

    private final AuthConfigProperties authConfig;

    private final EmailVerificationCodeStoreService emailVerificationCodeStoreService;

    private final ErrorMessageProperties errorMessage;

    @Override
    public UUID registerClient(ClientRegistrationForm registrationForm) {
        registrationFormValidator.validateClientRegistrationForm(registrationForm);
        return registerUser(registrationForm, false);
    }

    @Override
    public UUID registerDentist(DentistRegistrationForm registrationForm) {
        registrationFormValidator.validateDentistRegistrationForm(registrationForm);
        return registerUser(registrationForm, true);
    }

    private UUID registerUser(BaseRegistrationForm registrationForm, boolean isDentist) {
        String email = registrationForm.getEmail();
        if (!checkEmailIsAvailable(email)) {
            throw new BadRequestException(errorMessage.emailAlreadyExists(), ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        UUID userId = userDataService.createUserData(registrationForm, isDentist);
        String hashedPassword = passwordEncoder.encode(registrationForm.getPassword());
        userCredentialsService.createLocalUserCredentials(userId, email, hashedPassword);
        sendAndStoreEmailVerificationCode(email);
        return userId;
    }

    private void sendAndStoreEmailVerificationCode(String email) {
        String verificationCode = generateEmailVerificationCode();
        emailVerificationCodeStoreService.storeEmailVerificationCode(email, verificationCode);
        mailService.sendEmailWithVerificationCode(email, verificationCode);
    }

    private String generateEmailVerificationCode() {
        int length = authConfig.emailVerificationCodeLength();
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length) - 1;
        return String.valueOf(min + new SecureRandom().nextInt(max - min + 1));
    }

    @Override
    public void verify(String email, String verificationCode) {
        if (userDataService.checkIsEmailNotVerified(email)) {
            if (emailVerificationCodeStoreService.getEmailConfirmationCode(email) == null) {
                sendAndStoreEmailVerificationCode(email);
                throw new BadRequestException(errorMessage.emailCodeExpired(), ErrorCode.EMAIL_CODE_EXPIRED);
            }
            if (emailVerificationCodeStoreService.validateEmailConfirmationCode(email, verificationCode)) {
                userDataService.verifyUserEmail(email);
                emailVerificationCodeStoreService.deleteEmailConfirmationCode(email);
                clientService.createClientByUserEmail(email);
            } else {
                throw new BadRequestException(errorMessage.emailCodeInvalid(), ErrorCode.EMAIL_CODE_INVALID);
            }
        } else {
            throw new ForbiddenException(errorMessage.emailAlreadyVerified(), ErrorCode.EMAIL_ALREADY_VERIFIED);
        }
    }

    @Override
    public JwtTokensResponse login(LoginForm loginForm) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();
        if (userCredentialsService.checkLoginLocalCredentials(email, password)) {
            Map<String, Object> jwtClaims = userDataService.getJwtClaimsMapByEmail(email);
            return generateTokens(email, jwtClaims);
        } else {
            throw new BadRequestException(errorMessage.incorrectLoginCredentials(), ErrorCode.INCORRECT_LOGIN_CREDENTIALS);
        }
    }

    @Override
    public boolean checkEmailIsAvailable(String email) {
        return userDataService.checkIsEmailAvailable(email);
    }

    @Override
    public AccessTokenResponse refresh(String refreshToken) {
        String email = jwtProvider.extractSubject(refreshToken);
        Map<String, Object> jwtClaims = userDataService.getJwtClaimsMapByEmail(email);
        UUID userId = (UUID) jwtClaims.get("userId");
        if (refreshTokenStoreService.validateRefreshToken(userId, refreshToken)) {
            return new AccessTokenResponse(jwtProvider.generateAccessToken(email, jwtClaims));
        }
        throw new UnauthorizedException(errorMessage.refreshTokenInvalid(), ErrorCode.REFRESH_TOKEN_INVALID);
    }

    private JwtTokensResponse generateTokens(String email, Map<String, Object> claims) {
        UUID userId = (UUID) claims.get(authConfig.jwtUserIdKey());
        String refreshToken = jwtProvider.generateRefreshToken(email);
        String accessToken = jwtProvider.generateAccessToken(email, claims);
        refreshTokenStoreService.storeRefreshToken(userId, refreshToken);
        return JwtTokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logout(UUID userId) {
        refreshTokenStoreService.deleteRefreshToken(userId);
    }
}

