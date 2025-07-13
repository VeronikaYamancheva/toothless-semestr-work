package ru.itis.vhsroni.comment.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(description = "Response after successful comment creation")
public record CreateCommentResponse(
        @Schema(description = "Unique identifier of the created comment", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID commentId,

        @Schema(description = "Indicates if comment was created successfully", example = "true")
        boolean isSuccess
) {}