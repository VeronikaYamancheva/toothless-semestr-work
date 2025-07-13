package ru.itis.vhsroni.profile.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import ru.itis.vhsroni.auth.util.validator.annotation.AfterYear;

import java.time.LocalDate;

import static ru.itis.vhsroni.config.property.ValidationConstants.*;

@Schema(description = "Request to update basic profile information")
public record UpdateBaseProfileInfoRequest(
        @NotBlank(message = "First name should not be empty")
        @Size(
                min = FIRST_NAME_MIN_LENGTH, max = FIRST_NAME_MAX_LENGTH,
                message = "First name must be between {min} and {max} characters"
        )
        @Schema(
                description = "User's first name", example = "John",
                minLength = FIRST_NAME_MIN_LENGTH, maxLength = FIRST_NAME_MAX_LENGTH,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String firstName,

        @NotBlank(message = "First name should not be empty")
        @Size(
                min = LAST_NAME_MIN_LENGTH, max = LAST_NAME_MAX_LENGTH,
                message = "First name must be between {min} and {max} characters"
        )
        @Schema(
                description = "User's last name", example = "Doe",
                minLength = LAST_NAME_MIN_LENGTH, maxLength = LAST_NAME_MAX_LENGTH,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String lastName,

        @Size(
                min = MIDDLE_NAME_MIN_LENGTH, max = MIDDLE_NAME_MAX_LENGTH,
                message = "Patronymic must be between {min} and {max} characters"
        )
        @Schema(
                description = "User's middle name (patronymic)", example = "Alexandrovich",
                minLength = MIDDLE_NAME_MIN_LENGTH, maxLength = MIDDLE_NAME_MAX_LENGTH,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String middleName,

        @NotNull(message = "Birth date must not be null")
        @Past(message = "Birth date must be in the past")
        @AfterYear(year = BIRTHDATE_MIN_YEAR)
        @Schema(
                description = "User's birth date in ISO format (YYYY-MM-DD)", example = "1990-01-01",
                type = "string", format = "date",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        LocalDate birthDate,

        @NotBlank(message = "Phone should not be empty")
        @Pattern(regexp = PHONE_PATTERN, message = "Phone number must be in format +7 (XXX) XXX-XX-XX)")
        @Schema(
                description = "User's phone number in Russian format", example = "+7 (123) 456-78-90",
                pattern = PHONE_PATTERN,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String phoneNumber
) {
}
