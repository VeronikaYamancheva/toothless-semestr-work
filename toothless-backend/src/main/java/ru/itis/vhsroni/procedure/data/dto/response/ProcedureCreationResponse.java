package ru.itis.vhsroni.procedure.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(description = "Response after successful comment creation")
public record ProcedureCreationResponse(
        @Schema(description = "Unique identifier of the created procedure", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID procedureId,

        @Schema(description = "Indicates if procedure was created successfully", example = "true")
        boolean isSuccess
) {}
