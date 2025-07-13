package ru.itis.vhsroni.auth.util.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.itis.vhsroni.auth.util.validator.validator.AfterYearValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AfterYearValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterYear {
    String message() default "Date should be after {year} year";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int year() default 1900;
}
