package ru.itis.vhsroni.profile.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.itis.vhsroni.auth.util.validator.annotation.StrongPassword;

import static ru.itis.vhsroni.config.property.ValidationConstants.*;

@Schema(description = "Request to update user password")
public record UpdatePasswordRequest (

        @Schema(
                description = "Current password for verification",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String oldPassword,

        @NotBlank(message = "Password should not be empty")
        @Size(
                min = RAW_PASSWORD_MIN_LENGTH, max = RAW_PASSWORD_MAX_LENGTH,
                message = "Password must be between {min} and {max} characters"
        )
        @StrongPassword
        @Schema(
                description = "New password (must meet complexity requirements)",
                example = "NewSecurePass123!",
                minLength = RAW_PASSWORD_MIN_LENGTH,
                maxLength = RAW_PASSWORD_MAX_LENGTH,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String newPassword,

        @NotBlank(message = "Confirm password should not be empty")
        @Schema(
                description = "Confirmation of new password (must match newPassword)",
                example = "NewSecurePass123!",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String newConfirmPassword
){
}