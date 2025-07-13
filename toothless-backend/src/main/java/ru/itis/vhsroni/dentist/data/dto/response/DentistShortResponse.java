package ru.itis.vhsroni.dentist.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(description = "Brief information about a dentist for listings and quick reference")
public record DentistShortResponse(
        @Schema(description = "Unique identifier of the dentist",example = "123e4567-e89b-12d3-a456-426614174000")
        UUID dentistId,

        @Schema(description = "Associated user ID from authentication system",example = "123e4567-e89b-12d3-a456-426614174000")
        UUID userId,

        @Schema(
                description = "First name of the dentist",
                example = "Ivan",
                minLength = 2,
                maxLength = 50
        )
        String firstName,

        @Schema(
                description = "Last name of the dentist",
                example = "Petrov",
                minLength = 2,
                maxLength = 50
        )
        String lastName,

        @Schema(
                description = "Patronymic (middle name) of the dentist",
                example = "Sergeevich",
                minLength = 2,
                maxLength = 50,
                nullable = true
        )
        String middleName,

        @Schema(
                description = "Professional specialization area",
                example = "Orthodontist"
        )
        String specialization
) {}