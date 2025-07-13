package ru.itis.vhsroni.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mail")
public record MailConfigProperties(
        String senderUsername,
        String verificationCodeSubject,
        String verificationCodeText
) {
}

