package ru.itis.vhsroni.profile.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Request to update dentist-specific profile information")
public record UpdateDentistProfileInfoRequest(
        @Schema(
                description = "ID of dentist's specialization", example = "550e8400-e29b-41d4-a716-446655440000",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        UUID specializationId,

        @Schema(
                description = "Years of professional experience", example = "5",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Integer experience,

        @Schema(
                description = "Education information", example = "Moscow State Medical University",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String education,

        @Schema(
                description = "Additional professional information", example = "Specializes in pediatric dentistry",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String about
) {
}