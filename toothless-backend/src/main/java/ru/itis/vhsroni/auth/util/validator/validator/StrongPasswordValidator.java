package ru.itis.vhsroni.auth.util.validator.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.itis.vhsroni.auth.util.validator.annotation.StrongPassword;

import static ru.itis.vhsroni.config.property.ValidationConstants.*;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isBlank()) {
            return false;
        }
        if (password.length() < RAW_PASSWORD_MIN_LENGTH) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must contain at least 8 characters long")
                    .addConstraintViolation();
            return false;
        }
        if (!password.matches("^[a-zA-Z0-9!@#$%^&*()_+]+$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "The password must contain only numbers, Latin uppercase or lowercase letters and special characters"
            ).addConstraintViolation();
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Password must contain at least one uppercase letter"
            ).addConstraintViolation();
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Password must contain at least one lowercase letter"
            ).addConstraintViolation();
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Password must contain at least one digit"
            ).addConstraintViolation();
            return false;
        }
        if (!password.matches(".*[!@#$%^&*()_+].*")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Password must contain at least one special character (!@#$%^&*()_+)"
            ).addConstraintViolation();
            return false;
        }
        return true;
    }
}

