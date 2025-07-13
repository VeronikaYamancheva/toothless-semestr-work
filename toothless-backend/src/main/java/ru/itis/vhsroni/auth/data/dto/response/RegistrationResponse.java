package ru.itis.vhsroni.auth.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(name = "RegistrationResponse", description = "Response for register-user operation")
public record RegistrationResponse(
        @Schema(description = "Boolean flag - registration ended without errors", example = "false")
        boolean isSuccess,

        @Schema(description = "New user id in UUID format", example = "3e4d5c6b-7a8f-9e0d-1c2b-3a4e5f6d7b8c")
        UUID userId
) {
}
