package ru.itis.vhsroni.appointment.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(description = "Complete medical form for appointment")
public record AppointmentFormResponse(
        @Schema(description = "Appointment identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID appointmentId,

        @Schema(description = "Expected procedure name", example = "Dental cleaning")
        String expectedProcedure,

        @Schema(description = "Patient complaints", example = "Tooth sensitivity")
        String complaints,

        @Schema(description = "Observed symptoms", example = "Pain when consuming cold drinks")
        String symptoms,

        @Schema(description = "Doctor's diagnosis", example = "Dentin hypersensitivity")
        String diagnosis,

        @Schema(description = "Procedures performed", example = "Fluoride application")
        String proceduresDone,

        @Schema(description = "Doctor's recommendations", example = "Use desensitizing toothpaste")
        String recommendations,

        @Schema(description = "Proposed treatment plan", example = "Follow-up in 2 weeks")
        String treatmentPlan
) {}
