package ru.itis.vhsroni.util.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Represents a user's full name components")
public record UserFullName(
        @Schema(description = "User's first/given name", example = "John")
        String firstName,

        @Schema(description = "User's last/family name", example = "Doe")
        String lastName,

        @Schema(description = "User's middle name or patronymic (optional)", example = "Michael")
        String middleName
) {
}
