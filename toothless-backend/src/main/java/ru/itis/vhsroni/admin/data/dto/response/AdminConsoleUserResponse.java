package ru.itis.vhsroni.admin.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.itis.vhsroni.auth.data.enums.Role;

import java.util.List;
import java.util.UUID;

@Builder
@Schema(description = "User details response for admin console")
public record AdminConsoleUserResponse(
        @Schema(description = "Unique user identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID userId,

        @Schema(description = "User's first name", example = "John")
        String firstName,

        @Schema(description = "User's last name", example = "Doe")
        String lastName,

        @Schema(description = "User's middle name (optional)", example = "Michael", nullable = true)
        String middleName,

        @Schema(description = "List of user roles", example = "[\"DENTIST\", \"CLIENT\"]")
        List<Role> roles,

        @Schema(description = "Indicates if user has ADMIN role", example = "true")
        Boolean isAdmin,

        @Schema(description = "Indicates if user has BANNED state", example = "false")
        Boolean isBanned
) {
}