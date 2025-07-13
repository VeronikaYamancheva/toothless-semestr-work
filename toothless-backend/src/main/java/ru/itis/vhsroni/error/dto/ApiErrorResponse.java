package ru.itis.vhsroni.error.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.itis.vhsroni.error.enums.ErrorCode;

@Builder
@Schema(description = "Standard error response for API")
public record ApiErrorResponse(
        @Schema(description = "HTTP status code", example = "500")
        int code,

        @Schema(description = "Error message", example = "Something went wrong")
        String exceptionMessage,

        @Schema(description = "Enum value with error-tag", example = "EMAIL_NOT_VERIFIED")
        ErrorCode errorCode
) {
}