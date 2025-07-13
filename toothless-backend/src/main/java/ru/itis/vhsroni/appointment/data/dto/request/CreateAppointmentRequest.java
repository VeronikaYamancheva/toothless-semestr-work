package ru.itis.vhsroni.appointment.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Schema(description = "Request to create a new appointment")
public record CreateAppointmentRequest (
        @NotNull(message = "Dentist can not be empty")
        @Schema(description = "ID of the dentist", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID dentistId,

        @NotNull(message = "Appointment begin time can not be empty")
        @Schema(description = "Appointment start time", example = "09:00:00")
        LocalTime beginTime,

        @NotNull(message = "Appointment end time can not be empty")
        @Schema(description = "Appointment end time", example = "10:00:00")
        LocalTime endTime,

        @NotNull(message = "Appointment date can not be empty")
        @FutureOrPresent
        @Schema(description = "Appointment date (must be today or in the future)", example = "2023-12-31")
        LocalDate date,

        @Schema(description = "ID of the procedure (optional)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", nullable = true)
        UUID procedureId
){
}