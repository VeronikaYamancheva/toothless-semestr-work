package ru.itis.vhsroni.comment.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.itis.vhsroni.util.dto.UserFullName;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Schema(description = "Comment information with author and context details")
public record CommentResponse(
        @Schema(description = "Unique identifier of the comment", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID commentId,

        @Schema(description = "Author information")
        AuthorResponse author,

        @Schema(description = "Dentist information (null for main page comments)")
        UserFullName dentist,

        @Schema(description = "Comment content text", example = "Excellent service!")
        String content,

        @Schema(
                description = "Date and time when comment was created in ISO format",
                example = "2023-05-15T14:30:00",
                type = "string",
                format = "date-time"
        )
        LocalDateTime dateTime,

        @Schema(description = "Root comment information (for replies)")
        RootCommentResponse rootComment
) {
    @Builder
    @Schema(description = "Author information")
    public record AuthorResponse(
            @Schema(description = "Author's user ID",example = "550e8400-e29b-41d4-a716-446655440000")
            UUID userId,

            @Schema(description = "Author's first name", example = "John")
            String firstName,

            @Schema(description = "Author's last name", example = "Doe")
            String lastName
    ) {
    }

    @Builder
    @Schema(description = "Root comment information for replies")
    public record RootCommentResponse (
            @Schema(description = "Root comment author first name", example = "Anna")
            String authorFirstName,

            @Schema(description = "Root comment author last name", example = "Petrova")
            String authorLastName
    ){}
}