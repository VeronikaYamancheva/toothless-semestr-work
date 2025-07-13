package ru.itis.vhsroni.auth.data.dto.inner;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateOAuthUserData(
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthDate,
        String avatarUrl,
        String email
) {
}
