package ru.itis.vhsroni.appointment.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import static ru.itis.vhsroni.config.property.ValidationConstants.*;

@Schema(description = "Request to update appointment form details")
public record UpdateAppointmentFormRequest(
        @Size(max = APPOINTMENT_FORM_TEXT_MAX_LENGTH, message = "Form text can not have length more than {} characters")
        @Schema(description = "Patient's complaints", example = "Tooth pain when chewing", nullable = true)
        String complaints,

        @Size(max = APPOINTMENT_FORM_TEXT_MAX_LENGTH, message = "Form text can not have length more than {} characters")
        @Schema(description = "Observed symptoms", example = "Gum swelling, sensitivity to cold", nullable = true)
        String symptoms,

        @Size(max = APPOINTMENT_FORM_TEXT_MAX_LENGTH, message = "Form text can not have length more than {} characters")
        @Schema(description = "Diagnosis", example = "Pulpitis", nullable = true)
        String diagnosis,

        @Size(max = APPOINTMENT_FORM_TEXT_MAX_LENGTH, message = "Form text can not have length more than {} characters")
        @Schema(description = "Procedures performed", example = "Root canal cleaning, temporary filling", nullable = true)
        String proceduresDone,

        @Size(max = APPOINTMENT_FORM_TEXT_MAX_LENGTH, message = "Form text can not have length more than {} characters")
        @Schema(description = "Recommendations for patient", example = "Take nimesil for 3 days, avoid hard foods", nullable = true)
        String recommendations,

        @Size(max = APPOINTMENT_FORM_TEXT_MAX_LENGTH, message = "Form text can not have length more than {} characters")
        @Schema(description = "Treatment plan", example = "Permanent filling after 3 days", nullable = true)
        String treatmentPlan
){
}