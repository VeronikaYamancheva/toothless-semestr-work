package ru.itis.vhsroni.profile.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Schema(
        description = "Dentist profile information with professional details",
        allOf = BaseProfileResponse.class
)
public class DentistProfileResponse extends BaseProfileResponse {
    @Schema(
            description = "Dentist's specialization",
            example = "Orthodontist"
    )
    private String specialization;

    @Schema(
            description = "Years of professional experience",
            example = "5",
            minimum = "0"
    )
    private Integer experience;

    @Schema(
            description = "Education information",
            example = "Moscow State Medical University"
    )
    private String education;

    @Schema(
            description = "Additional professional information",
            example = "Specializes in pediatric dentistry"
    )
    private String about;
}