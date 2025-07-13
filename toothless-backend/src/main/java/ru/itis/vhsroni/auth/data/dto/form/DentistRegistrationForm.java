package ru.itis.vhsroni.auth.data.dto.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static ru.itis.vhsroni.config.property.ValidationConstants.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(name = "DentistRegistrationForm", description = "Extension for BaseRegistration form for dentist")
public class DentistRegistrationForm extends BaseRegistrationForm {

    @NotBlank(message = "Access code should not be empty")
    @Size(
            min = DENTIST_ACCESS_CODE_LENGTH,
            max = DENTIST_ACCESS_CODE_LENGTH,
            message = "Access code should be {min}-digits"
    )
    @Schema(
            description = "Special access code for dentist registration",
            example = "123456",
            minLength = DENTIST_ACCESS_CODE_LENGTH,
            maxLength = DENTIST_ACCESS_CODE_LENGTH
    )
    private String accessCode;
}