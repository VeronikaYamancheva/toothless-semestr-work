package ru.itis.vhsroni.profile.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to update client-specific profile information")
public record UpdateClientProfileInfoRequest(
        @Schema(
                description = "Passport full number (series and number)", example = "4510 123456",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String passportFullNumber,

        @Schema(
                description = "Authority that issued the passport", example = "ОУФМС России по г. Москве",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String passportIssuedBy,

        @Schema(
                description = "Passport issue date in format DD.MM.YYYY", example = "15.05.2015",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String passportIssueDate,

        @Schema(
                description = "Passport division code", example = "770-123",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String passportDivisionCode,

        @Schema(
                description = "Medical insurance policy number", example = "1234567890123456",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String policyNumber,

        @Schema(
                description = "SNILS number (11 digits)", example = "123-456-789 01",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String snils
){
}