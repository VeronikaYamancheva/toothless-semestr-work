package ru.itis.vhsroni.config.property;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "encryption")
public record EncryptionConfigProperties(
        String publicKey,
        String privateKey
) {
}