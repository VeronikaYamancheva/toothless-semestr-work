package ru.itis.vhsroni.appointment.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.itis.vhsroni.util.dto.UserFullName;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
@Schema(description = "Short appointment information for admin view")
public record AdminAppointmentShortResponse(
        @Schema(description = "Unique appointment identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID appointmentId,

        @Schema(description = "Client's full name information")
        UserFullName client,

        @Schema(description = "Dentist's full name information")
        UserFullName dentist,

        @Schema(description = "Appointment date", example = "2023-12-31")
        LocalDate date,

        @Schema(description = "Appointment start time", example = "09:00:00")
        LocalTime beginTime,

        @Schema(description = "Appointment end time", example = "10:00:00")
        LocalTime endTime
) {}
