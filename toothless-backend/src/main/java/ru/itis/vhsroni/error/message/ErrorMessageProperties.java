package ru.itis.vhsroni.error.message;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exception")
public record ErrorMessageProperties(

        String noRequiredRole,
        String userDataNotFound,
        String procedureNotFound,
        String incorrectTimeSlot,
        String appointmentHasAlreadyBooked,
        String clientAlreadyHasAppointment,
        String pastDate,
        String pastTime,
        String lunchTime,
        String dentistNotFound,
        String appointmentNotFound,
        String emailAlreadyExists,
        String emailCodeExpired,
        String emailCodeInvalid,
        String emailAlreadyVerified,
        String incorrectLoginCredentials,
        String refreshTokenInvalid,
        String accountNotVerified,
        String accountBanned,
        String passwordsDoNotMatch,
        String clientNotFound,
        String failedUploadAvatar,
        String failedGetAvatar,
        String avatarNotFound,
        String specializationNotFound,
        String dentistCodeIncorrect,
        String unauthorized,
        String forbidden,
        String invalidInputs,
        String internalError,
        String invalidJwt,
        String avatarUrlNotFound,
        String invalidAvatarUrl,
        String emptyFile,
        String invalidFile,
        String avatarNotFoundInStorage

) {
}
