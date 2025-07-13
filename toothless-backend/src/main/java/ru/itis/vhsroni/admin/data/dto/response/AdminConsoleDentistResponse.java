package ru.itis.vhsroni.admin.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(description = "Dentist information response for admin console")
public record AdminConsoleDentistResponse(
        @Schema(description = "Unique dentist user identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID userId,

        @Schema(description = "Dentist's first name", example = "Sarah")
        String firstName,

        @Schema(description = "Dentist's last name", example = "Johnson")
        String lastName,

        @Schema(description = "Dentist's middle name (optional)", example = "Elizabeth", nullable = true)
        String middleName
) {
}