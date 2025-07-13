package ru.itis.vhsroni.auth.util.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.vhsroni.config.property.AuthConfigProperties;
import ru.itis.vhsroni.util.converter.EncryptConverter;

@Component
@RequiredArgsConstructor
public class AuthCookieHelper {

    private final AuthConfigProperties authConfig;

    private final EncryptConverter encryptConverter;

    public void setRefreshTokenCookieToHttpResponse(HttpServletResponse httpServletResponse, String refreshToken) {
        Cookie cookie = new Cookie(authConfig.refreshTokenCookieKey(), refreshToken);
        cookieDefaultSetting(cookie);
        cookie.setMaxAge((int) authConfig.refreshTokenExpiration().getSeconds());
        httpServletResponse.addCookie(cookie);
    }

    public void setEmailCookieToHttpResponse(HttpServletResponse httpServletResponse, String email) {
        String encryptedEmail = encryptConverter.convertToDatabaseColumn(email);
        Cookie cookie = new Cookie(authConfig.emailCookieKey(), encryptedEmail);
        cookieDefaultSetting(cookie);
        cookie.setMaxAge((int)authConfig.emailVerificationCodeExpiration().getSeconds());
        httpServletResponse.addCookie(cookie);
    }

    public void deleteRefreshTokenCookie(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(authConfig.refreshTokenCookieKey(), null);
        cookieDefaultSetting(cookie);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }

    public void deleteEmailCookie(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(authConfig.emailCookieKey(), null);
        cookieDefaultSetting(cookie);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }

    public void deleteJsessionIdCookie(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookieDefaultSetting(cookie);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }

    public String decryptEmailCookie(String encryptedEmail){
        return encryptConverter.convertToEntityAttribute(encryptedEmail);
    }

    private void cookieDefaultSetting(Cookie cookie){
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "Lax");
    }
}
