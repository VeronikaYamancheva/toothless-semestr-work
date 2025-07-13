package ru.itis.vhsroni.profile.data.dto.inner;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BaseProfileInfo (
        String firstName,
        String lastName,
        String middleName,
        LocalDate birthDate,
        String email,
        String phoneNumber,
        boolean adminApproval
){}
