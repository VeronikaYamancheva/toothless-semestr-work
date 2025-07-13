package ru.itis.vhsroni.auth.data.dto.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.itis.vhsroni.auth.util.validator.annotation.AfterYear;
import ru.itis.vhsroni.auth.util.validator.annotation.StrongPassword;

import java.time.LocalDate;

import static ru.itis.vhsroni.config.property.ValidationConstants.*;
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "BaseRegistrationForm",
        description = "Base registration form containing common fields for all user types. Used for client and dentist registration."
)
public abstract class BaseRegistrationForm {

    @NotBlank(message = "First name should not be empty")
    @Size(
            min = FIRST_NAME_MIN_LENGTH,
            max = FIRST_NAME_MAX_LENGTH,
            message = "First name must be between {min} and {max} characters"
    )
    @Schema(
            description = "User's first name",
            example = "John",
            minLength = FIRST_NAME_MIN_LENGTH,
            maxLength = FIRST_NAME_MAX_LENGTH
    )
    protected String firstName;

    @NotBlank(message = "Last name should not be empty")
    @Size(
            min = LAST_NAME_MIN_LENGTH,
            max = LAST_NAME_MAX_LENGTH,
            message = "Last name must be between {min} and {max} characters"
    )
    @Schema(
            description = "User's last name",
            example = "Doe",
            minLength = LAST_NAME_MIN_LENGTH,
            maxLength = LAST_NAME_MAX_LENGTH
    )
    protected String lastName;

    @Size(
            min = MIDDLE_NAME_MIN_LENGTH,
            max = MIDDLE_NAME_MAX_LENGTH,
            message = "Patronymic must be between {min} and {max} characters"
    )
    @Schema(
            description = "User's patronymic (middle name)",
            example = "Alexandrovich",
            minLength = MIDDLE_NAME_MIN_LENGTH,
            maxLength = MIDDLE_NAME_MAX_LENGTH,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    protected String middleName;

    @NotNull(message = "Birth date must not be null")
    @Past(message = "Birth date must be in the past")
    @AfterYear(year = BIRTHDATE_MIN_YEAR)
    @Schema(
            description = "User's birth date. Must be in the past and not earlier than year " + BIRTHDATE_MIN_YEAR,
            example = "1990-01-01",
            type = "string",
            format = "date"
    )
    protected LocalDate birthDate;

    @NotBlank(message = "Email should not be empty")
    @Size(
            min = EMAIL_MIN_LENGTH,
            max = EMAIL_MAX_LENGTH,
            message = "Email must be between {min} and {max} characters"
    )
    @Email(message = "Email must be valid")
    @Schema(
            description = "User's email address",
            example = "user@example.com",
            minLength = EMAIL_MIN_LENGTH,
            maxLength = EMAIL_MAX_LENGTH,
            format = "email"
    )
    protected String email;

    @NotBlank(message = "Phone should not be empty")
    @Pattern(
            regexp = PHONE_PATTERN,
            message = "Phone number must be in format +7 (XXX) XXX-XX-XX"
    )
    @Schema(
            description = "User's phone number in Russian format",
            example = "+7 (123) 456-78-90",
            pattern = PHONE_PATTERN
    )
    protected String phoneNumber;

    @NotBlank(message = "Password should not be empty")
    @Size(
            min = RAW_PASSWORD_MIN_LENGTH,
            max = RAW_PASSWORD_MAX_LENGTH,
            message = "Password must be between {min} and {max} characters"
    )
    @StrongPassword
    @Schema(
            description = "User's password. Must be strong and contain at least one uppercase, one lowercase, one digit, and one special character.",
            example = "SecurePass123!",
            minLength = RAW_PASSWORD_MIN_LENGTH,
            maxLength = RAW_PASSWORD_MAX_LENGTH
    )
    protected String password;

    @NotBlank(message = "Confirmation password should not be empty")
    @Schema(
            description = "Password confirmation (must match the password field)",
            example = "SecurePass123!"
    )
    protected String confirmPassword;
}
