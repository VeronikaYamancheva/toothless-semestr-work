package ru.itis.vhsroni.comment.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.UUID;

import static ru.itis.vhsroni.config.property.ValidationConstants.*;

@Builder
@Schema(description = "Request to create new comment or reply")
public record CreateCommentRequest(
        @NotBlank(message = "Content can not be empty")
        @Size(max = COMMENT_CONTENT_MAX_LENGTH, message = "Comment length can not be more than {} characters")
        @Schema(
                description = "Content of the comment",
                example = "This is a great dentist!",
                maxLength = COMMENT_CONTENT_MAX_LENGTH,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String content,

        @Schema(
                description = "ID of the dentist being commented",
                example = "123e4567-e89b-12d3-a456-426614174000",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        UUID dentistId,

        @Schema(
                description = "ID of parent comment if this is a reply",
                example = "123e4567-e89b-12d3-a456-426614174000",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        UUID parentId
) {
}