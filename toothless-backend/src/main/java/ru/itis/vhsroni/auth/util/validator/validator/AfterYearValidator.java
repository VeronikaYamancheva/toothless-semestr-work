package ru.itis.vhsroni.auth.util.validator.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.itis.vhsroni.auth.util.validator.annotation.AfterYear;

import java.time.LocalDate;

public class AfterYearValidator implements ConstraintValidator<AfterYear, LocalDate> {

    private int minYear;

    @Override
    public void initialize(AfterYear constraintAnnotation) {
        this.minYear = constraintAnnotation.year();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        LocalDate minDate = LocalDate.of(minYear, 1, 1);
        return value.isAfter(minDate);
    }
}

