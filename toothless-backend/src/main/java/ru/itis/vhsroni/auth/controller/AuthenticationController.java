package ru.itis.vhsroni.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.vhsroni.auth.api.AuthenticationApi;
import ru.itis.vhsroni.auth.data.dto.form.ClientRegistrationForm;
import ru.itis.vhsroni.auth.data.dto.form.DentistRegistrationForm;
import ru.itis.vhsroni.auth.data.dto.form.LoginForm;
import ru.itis.vhsroni.auth.data.dto.inner.JwtTokensResponse;
import ru.itis.vhsroni.auth.data.dto.response.*;
import ru.itis.vhsroni.auth.data.principal.UnifiedAuthPrincipal;
import ru.itis.vhsroni.auth.service.AuthenticationService;
import ru.itis.vhsroni.auth.util.cookie.AuthCookieHelper;
import ru.itis.vhsroni.error.dto.OperationResponse;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.ForbiddenException;
import ru.itis.vhsroni.error.exception.UnauthorizedException;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    private final AuthCookieHelper authCookieHelper;

    @Override
    public RegistrationResponse registerClient(ClientRegistrationForm clientRegistrationForm,
                                               HttpServletResponse httpServletResponse) {
        UUID userId = authenticationService.registerClient(clientRegistrationForm);
        String email = clientRegistrationForm.getEmail();
        authCookieHelper.setEmailCookieToHttpResponse(httpServletResponse, email);
        return RegistrationResponse.builder().userId(userId).isSuccess(true).build();
    }

    @Override
    public RegistrationResponse registerDentist(DentistRegistrationForm dentistRegistrationForm,
                                                HttpServletResponse httpServletResponse) {
        UUID userId = authenticationService.registerDentist(dentistRegistrationForm);
        String email = dentistRegistrationForm.getEmail();
        authCookieHelper.setEmailCookieToHttpResponse(httpServletResponse, email);
        return RegistrationResponse.builder().userId(userId).isSuccess(true).build();
    }

    @Override
    public OperationResponse verifyUser(String encryptedEmail, String verificationCode) {
        String decryptedEmail = authCookieHelper.decryptEmailCookie(encryptedEmail);
        authenticationService.verify(decryptedEmail, verificationCode);
        return OperationResponse.builder().isSuccess(true).build();
    }

    @Override
    public AccessTokenResponse login(LoginForm loginForm, HttpServletResponse response) {
        try {
            JwtTokensResponse jwtTokens = authenticationService.login(loginForm);
            authCookieHelper.setRefreshTokenCookieToHttpResponse(response, jwtTokens.refreshToken());
            return AccessTokenResponse.builder().accessToken(jwtTokens.accessToken()).build();
        } catch (ForbiddenException e) {
            if (e.getErrorCode() == ErrorCode.ACCOUNT_NOT_VERIFIED) {
                authCookieHelper.setEmailCookieToHttpResponse(response, loginForm.getEmail());
            }
            throw e;
        }
    }

    @Override
    public OperationResponse logout(UnifiedAuthPrincipal authPrincipal, HttpServletResponse httpServletResponse) {
        authenticationService.logout(authPrincipal.getUserId());
        authCookieHelper.deleteRefreshTokenCookie(httpServletResponse);
        authCookieHelper.deleteEmailCookie(httpServletResponse);
        authCookieHelper.deleteJsessionIdCookie(httpServletResponse);
        return OperationResponse.builder().isSuccess(true).build();
    }

    @Override
    public AccessTokenResponse refreshAccessToken(String refreshToken, HttpServletResponse response) {
        try {
            return authenticationService.refresh(refreshToken);
        } catch (UnauthorizedException e) {
            authCookieHelper.deleteRefreshTokenCookie(response);
            authCookieHelper.deleteEmailCookie(response);
            authCookieHelper.deleteJsessionIdCookie(response);
            throw e;
        }
    }

    @Override
    public EmailAvailabilityResponse checkEmailIsAvailable(String email) {
        boolean isAvailable = authenticationService.checkEmailIsAvailable(email);
        return EmailAvailabilityResponse.builder().isAvailable(isAvailable).build();
    }
}
