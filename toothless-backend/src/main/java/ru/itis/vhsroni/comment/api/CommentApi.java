package ru.itis.vhsroni.comment.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.vhsroni.auth.data.principal.UnifiedAuthPrincipal;
import ru.itis.vhsroni.comment.data.dto.request.CreateCommentRequest;
import ru.itis.vhsroni.comment.data.dto.response.CommentResponse;
import ru.itis.vhsroni.comment.data.dto.response.CreateCommentResponse;
import ru.itis.vhsroni.error.dto.ApiErrorResponse;
import ru.itis.vhsroni.error.dto.OperationResponse;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Comment API",
        description = "Endpoints for managing comments on dentists and main page"
)
@RequestMapping(path = "/api/v1/comments", produces = "application/json")
public interface CommentApi {

    @Operation(
            summary = "Get latest main page comments",
            description = "Returns a list of latest comments for display on main page",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Comments retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponse[].class)
                            )),
                    @ApiResponse(
                            responseCode = "400", description = "Invalid blocks count parameter",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )),
                    @ApiResponse(
                            responseCode = "500", description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            ))
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CommentResponse> getMainPageLatestComments(
            @Parameter(
                    name = "blocksCount",
                    description = "Number of comment blocks to return",
                    example = "5",
                    schema = @Schema(type = "integer", minimum = "1", maximum = "20")
            )
            @RequestParam int blocksCount
    );

    @Operation(
            summary = "Get comment replies",
            description = "Returns all replies to a specific comment",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Replies retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponse[].class)
                            )),
                    @ApiResponse(
                            responseCode = "500", description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            ))
            }
    )
    @GetMapping("/{commentId}/replies")
    @ResponseStatus(HttpStatus.OK)
    List<CommentResponse> getRepliesByCommentId(
            @Parameter(
                    description = "ID of the parent comment",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("commentId") UUID commentId
    );

    @Operation(
            summary = "Create new comment",
            description = "Creates a new comment on main page or reply to existing comment",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Comment created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreateCommentResponse.class)
                            )),
                    @ApiResponse(
                            responseCode = "400", description = "Invalid comment data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )),
                    @ApiResponse(
                            responseCode = "401", description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )),
                    @ApiResponse(
                            responseCode = "500", description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateCommentResponse createNewMainPageComment(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal,

            @Parameter(description = "Comment creation data", required = true)
            @Valid @RequestBody CreateCommentRequest createCommentRequest
    );

    @Operation(
            summary = "Delete comment",
            description = "Deletes a comment (only for comment owner or admin)",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Comment deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )),
                    @ApiResponse(
                            responseCode = "401", description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )),
                    @ApiResponse(
                            responseCode = "403", description = "Forbidden (not owner or admin)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@commentServiceImpl.isCommentOwner(#commentId, #authPrincipal.userId) or hasRole('ADMIN') or hasRole('MASTER')")
    OperationResponse deleteComment(
            @Parameter(
                    description = "ID of the comment to delete",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("commentId") UUID commentId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Get dentist's latest comments",
            description = "Returns latest comments for a specific dentist",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Comments retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponse[].class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Invalid blocks count parameter",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/dentist/{dentistId}")
    @ResponseStatus(HttpStatus.OK)
    List<CommentResponse> getDentistLatestComments(
            @Parameter(
                    name = "blocksCount",
                    description = "Number of comment blocks to return",
                    example = "5",
                    schema = @Schema(type = "integer", minimum = "1", maximum = "20")
            )
            int blocksCount,

            @Parameter(
                    description = "ID of the dentist",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("dentistId") UUID dentistId
    );
}