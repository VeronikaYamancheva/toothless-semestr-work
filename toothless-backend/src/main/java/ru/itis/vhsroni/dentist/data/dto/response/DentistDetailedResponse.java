package ru.itis.vhsroni.dentist.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Schema(description = "Detailed information about a dentist including personal details, specialization, experience and education")
public record DentistDetailedResponse(

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
                description = "Date of birth in ISO format (YYYY-MM-DD)",
                example = "1985-05-15",
                type = "string",
                format = "date"
        )
        LocalDate birthDate,

        @Schema(
                description = "Professional email address",
                example = "dentist.petrov@clinic.com",
                format = "email"
        )
        String email,

        @Schema(
                description = "Primary dental specialization",
                example = "Orthodontist"
        )
        String specialization,

        @Schema(
                description = "Years of professional experience",
                example = "7",
                minimum = "0",
                maximum = "60"
        )
        Integer experience,

        @Schema(
                description = "Educational background and degrees",
                example = "Moscow State Medical University, PhD in Dentistry"
        )
        String education,

        @Schema(
                description = "Additional professional information and bio",
                example = "Specializes in pediatric dentistry and orthodontics for children"
        )
        String about
) {}