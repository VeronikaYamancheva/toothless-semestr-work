package ru.itis.vhsroni.appointment.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
@Schema(description = "Detailed appointment information with complete client and dentist data for administrative purposes")
public record AdminAppointmentDetailedResponse(
        @Schema(description = "Unique identifier of the appointment", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID appointmentId,

        @Schema(description = "Complete client information including personal and contact details")
        ClientResponse client,

        @Schema(description = "Complete dentist information including professional details")
        DentistResponse dentist,

        @Schema(description = "Date of the scheduled appointment", example = "2023-12-31")
        LocalDate date,

        @Schema(description = "Scheduled start time of the appointment", example = "14:30:00")
        LocalTime beginTime,

        @Schema(description = "Scheduled end time of the appointment", example = "15:30:00")
        LocalTime endTime
) {
    @Builder
    @Schema(description = "Detailed dentist professional information")
    public record DentistResponse(
            @Schema(description = "Dentist's first name", example = "Anna")
            String firstName,

            @Schema(description = "Dentist's last name", example = "Petrova")
            String lastName,

            @Schema(description = "Dentist's middle name (optional)", example = "Viktorovna"
            )
            String middleName,

            @Schema(description = "Dentist's professional email address", example = "a.petrova@clinic.com")
            String email,

            @Schema(description = "Dentist's contact phone number", example = "+79161234567")
            String phoneNumber
    ) {}

    @Builder
    @Schema(description = "Detailed client personal information")
    public record ClientResponse(
            @Schema(description = "Client's first name", example = "Ivan")
            String firstName,

            @Schema(description = "Client's last name", example = "Smirnov")
            String lastName,

            @Schema(description = "Client's middle name (optional)", example = "Sergeevich")
            String middleName,

            @Schema(description = "Client's contact email", example = "i.smirnov@example.com")
            String email,

            @Schema(description = "Client's contact phone number", example = "+79031234567")
            String phoneNumber,

            @Schema(description = "Client's date of birth", example = "1985-07-15")
            LocalDate birthDate
    ) {}
}