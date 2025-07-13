package ru.itis.vhsroni.config.property;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "auth")
public record AuthConfigProperties(
        String privateKey,
        String publicKey,
        Duration accessTokenExpiration,
        Duration refreshTokenExpiration,
        String refreshTokenCookieKey,
        String emailCookieKey,
        String dentistAccessCode,
        int emailVerificationCodeLength,
        Duration emailVerificationCodeExpiration,
        String jwtSubjectKey,
        String jwtUserIdKey,
        String jwtRolesKey,
        String jwtNameKey
) {
}
