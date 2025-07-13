package ru.itis.vhsroni.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "logging")
public record LoggingConfigProperties(
        boolean enabled,
        boolean debug
) {
}

