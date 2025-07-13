package ru.itis.vhsroni.profile.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Schema(
        description = "Client profile information with personal documents data",
        allOf = BaseProfileResponse.class
)
public class ClientProfileResponse extends BaseProfileResponse {
    @Schema(
            description = "Passport full number (series and number)",
            example = "4510 123456"
    )
    private String passportFullNumber;

    @Schema(
            description = "Authority that issued the passport",
            example = "ОУФМС России по г. Москве"
    )
    private String passportIssuedBy;

    @Schema(
            description = "Passport issue date in format DD.MM.YYYY",
            example = "15.05.2015"
    )
    private String passportIssueDate;

    @Schema(
            description = "Passport division code",
            example = "770-123"
    )
    private String passportDivisionCode;

    @Schema(
            description = "Medical insurance policy number",
            example = "1234567890123456"
    )
    private String policyNumber;

    @Schema(
            description = "SNILS number (11 digits)",
            example = "123-456-789 01"
    )
    private String snils;
}