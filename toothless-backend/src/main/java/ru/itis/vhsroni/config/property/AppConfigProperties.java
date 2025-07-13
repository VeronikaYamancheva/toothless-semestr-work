package ru.itis.vhsroni.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.time.LocalTime;

@ConfigurationProperties(prefix = "app")
public record AppConfigProperties(
        String companyName,
        String frontendUrl,
        int dentistsDemoCount,
        int proceduresDemoCount,
        int commentsDemoCount,
        Duration timetableVisibility,
        LocalTime workDayBeginTime,
        LocalTime workDayEndTime,
        LocalTime lunchBeginTime,
        LocalTime lunchEndTime,
        Duration appointmentDuration
) {
}
