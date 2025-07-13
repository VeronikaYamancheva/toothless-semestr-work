package ru.itis.vhsroni.procedure.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(description = "Detailed information about a dental procedure")
public record ProcedureDetailedResponse (
        @Schema(description = "Unique identifier of the procedure", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID procedureId,

        @Schema(description = "Name of the procedure", example = "Teeth whitening")
        String name,

        @Schema(description = "Detailed description of the procedure", example = "Professional teeth whitening using advanced technology")
        String description,

        @Schema(description = "Cost of the procedure in rubles", example = "5000", minimum = "0")
        int cost,

        @Schema(description = "Dentist specialization's ID, mapped to this procedure", example = "5000", minimum = "0")
        UUID specializationId
) {}