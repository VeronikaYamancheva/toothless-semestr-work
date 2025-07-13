package ru.itis.vhsroni.error.dto;

import lombok.Builder;

@Builder
public record OperationResponse(
        boolean isSuccess
) {
}
