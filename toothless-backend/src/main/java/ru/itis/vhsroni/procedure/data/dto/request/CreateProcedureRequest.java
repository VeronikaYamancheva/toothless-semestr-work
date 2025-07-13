package ru.itis.vhsroni.procedure.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.UUID;
import static ru.itis.vhsroni.config.property.ValidationConstants.*;

@Builder
@Schema(description = "DTO for creating a new dental procedure")
public record CreateProcedureRequest(

        @NotBlank(message = "Procedure name should not be empty")
        @Size(
                min = PROCEDURE_NAME_MIN_LENGTH, max = PROCEDURE_NAME_MAX_LENGTH,
                message = "Procedure name length should be between {} and {}"
        )
        @Schema(description = "Name of the procedure", example = "Caries treatment")
        String name,

        @NotBlank(message = "Procedure description should not be empty")
        @Size(
                min = PROCEDURE_DESCRIPTION_MIN_LENGTH, max = PROCEDURE_DESCRIPTION_MAX_LENGTH,
                message = "Procedure description length should be between {} and {}"
        )
        @Schema(description = "Detailed description of the procedure", example = "Comprehensive caries treatment with anesthesia")
        String description,

        @Min(value = PROCEDURE_MIN_COST, message = "Procedure cost can not be less than 10$")
        @Max(value = PROCEDURE_MAX_COST, message = "Procedure cost can not be more than 5000$")
        @Schema(description = "Procedure cost in rubles", example = "5000")
        int cost,

        @NotNull(message = "Specialization can not be empty")
        @Schema(description = "ID of the procedure specialization", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID specializationId
) {}