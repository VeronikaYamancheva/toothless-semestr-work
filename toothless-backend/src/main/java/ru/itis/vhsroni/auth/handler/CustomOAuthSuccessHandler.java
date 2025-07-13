package ru.itis.vhsroni.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.itis.vhsroni.auth.data.principal.CustomOAuth2User;
import ru.itis.vhsroni.auth.jwt.JwtProvider;
import ru.itis.vhsroni.auth.service.RefreshTokenStoreService;
import ru.itis.vhsroni.auth.util.cookie.AuthCookieHelper;
import ru.itis.vhsroni.config.property.AppConfigProperties;

import java.io.IOException;
import java.util.UUID;

import static ru.itis.vhsroni.config.security.SecurityEndpoints.OAUTH_SUCCESS_REDIRECT_URI;

@Component
@RequiredArgsConstructor
public class CustomOAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    private final RefreshTokenStoreService refreshTokenStoreService;

    private final AuthCookieHelper authCookieHelper;

    private final AppConfigProperties appConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        UUID userId = oAuth2User.getUserId();
        String refreshToken = jwtProvider.generateRefreshToken(email);
        refreshTokenStoreService.storeRefreshToken(userId, refreshToken);
        authCookieHelper.setRefreshTokenCookieToHttpResponse(response, refreshToken);
        authCookieHelper.deleteJsessionIdCookie(response);
        String redirectLocation = appConfig.frontendUrl().concat(OAUTH_SUCCESS_REDIRECT_URI);
        response.sendRedirect(redirectLocation);
    }
}
