package ru.itis.vhsroni.error.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.itis.vhsroni.error.enums.ErrorCode;

import java.util.List;

@Builder
@Schema(description = "Response containing validation errors")
public record ValidationErrorResponse(
        @Schema(description = "HTTP status code", example = "400")
        int code,

        @Schema(description = "General error message", example = "Validation failed")
        String exceptionMessage,

        @Schema(description = "Enum value with error-tag", example = "EMAIL_NOT_VERIFIED")
        ErrorCode errorCode,

        @Schema(description = "List of field validation errors")
        List<ValidationError> validationErrors
) {
    @Builder
    @Schema(description = "Validation error for a specific field")
    public record ValidationError(
            @Schema(description = "Name of the invalid field", example = "email")
            String field,

            @Schema(description = "Error message for the field", example = "Email must be valid")
            String message
    ) {
    }
}