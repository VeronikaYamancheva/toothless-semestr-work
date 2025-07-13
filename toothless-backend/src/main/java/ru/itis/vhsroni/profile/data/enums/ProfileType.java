package ru.itis.vhsroni.profile.data.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Type of user profile")
public enum ProfileType {

    @Schema(description = "Profile includes base information and documents information")
    CLIENT,

    @Schema(description = "Profile includes base information and dentist characteristics")
    DENTIST,

    @Schema(description = "Profile includes only base information")
    ADMIN
}

