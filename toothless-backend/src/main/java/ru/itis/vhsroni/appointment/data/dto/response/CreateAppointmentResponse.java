package ru.itis.vhsroni.appointment.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema( description = "Response after appointment creation")
public record CreateAppointmentResponse(
        @Schema(description = "Created appointment identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID appointmentId,

        @Schema(description = "Indicates if appointment was successfully created", example = "true")
        boolean isSuccess
) {}
