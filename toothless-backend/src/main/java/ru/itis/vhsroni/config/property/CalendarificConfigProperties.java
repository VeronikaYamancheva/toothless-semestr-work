package ru.itis.vhsroni.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "calendarific")
public record CalendarificConfigProperties(
        String apiKey
) {
}

