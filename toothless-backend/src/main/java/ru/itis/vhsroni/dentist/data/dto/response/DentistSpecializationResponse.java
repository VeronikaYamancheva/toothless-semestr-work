package ru.itis.vhsroni.dentist.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(description = "Dental specialization type with identifier and display name")
public record DentistSpecializationResponse(
        @Schema(
                description = "Unique identifier for the specialization",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        UUID specializationId,

        @Schema(
                description = "Display name of the specialization",
                example = "Pediatric Dentistry"
        )
        String name
) {
}