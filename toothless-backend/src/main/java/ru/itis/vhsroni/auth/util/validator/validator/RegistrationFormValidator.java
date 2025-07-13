package ru.itis.vhsroni.auth.util.validator.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.vhsroni.auth.data.dto.form.BaseRegistrationForm;
import ru.itis.vhsroni.auth.data.dto.form.ClientRegistrationForm;
import ru.itis.vhsroni.auth.data.dto.form.DentistRegistrationForm;
import ru.itis.vhsroni.config.property.AuthConfigProperties;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.BadRequestException;

@Component
@RequiredArgsConstructor
public class RegistrationFormValidator {

    private final AuthConfigProperties authConfig;

    private final ErrorMessageProperties errorMessageProperties;

    public void validateClientRegistrationForm(ClientRegistrationForm form) {
        validateBaseRegistrationForm(form);
    }

    public void validateDentistRegistrationForm(DentistRegistrationForm form) {
        validateBaseRegistrationForm(form);
        String accessCode = form.getAccessCode();
        if (accessCode == null || accessCode.isBlank() || !accessCode.equals(authConfig.dentistAccessCode())) {
            throw new BadRequestException(errorMessageProperties.dentistCodeIncorrect(), ErrorCode.DENTIST_CODE_INCORRECT);
        }
    }

    private void validateBaseRegistrationForm(BaseRegistrationForm form) {
        String password = form.getPassword();
        String confirmPassword = form.getConfirmPassword();
        if (password == null || !password.equals(confirmPassword)) {
            throw new BadRequestException(errorMessageProperties.passwordsDoNotMatch(), ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }
    }
}
