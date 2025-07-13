package ru.itis.vhsroni.auth.data.dto.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(name = "ClientRegistrationForm", description = "Extension for BaseRegistration form for client")
public class ClientRegistrationForm extends BaseRegistrationForm {
}