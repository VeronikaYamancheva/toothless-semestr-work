package ru.itis.vhsroni.photo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.vhsroni.auth.data.principal.UnifiedAuthPrincipal;
import ru.itis.vhsroni.error.dto.ApiErrorResponse;
import ru.itis.vhsroni.error.dto.OperationResponse;

import java.util.UUID;

@Tag(
        name = "Photo API",
        description = "Endpoints for managing user avatars"
)
@RequestMapping(path = "/api/v1/photos")
public interface PhotoApi {


    @Operation(
            summary = "Get user avatar",
            description = "Returns avatar image for specified user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Avatar retrieved successfully",
                            content = @Content(
                                    mediaType = "image/*",
                                    schema = @Schema(type = "string", format = "binary")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Avatar not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/avatars/{userId}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<byte[]> getAvatar(
            @Parameter(
                    description = "User ID",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("userId") UUID userId
    );

    @Operation(
            summary = "Upload avatar",
            description = "Uploads new avatar for authenticated user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Avatar uploaded successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid file format",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/avatars")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse uploadAvatar(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal,

            @Parameter(
                    description = "Avatar image file",
                    required = true,
                    content = @Content(mediaType = "multipart/form-data")
            )
            @RequestPart("file") MultipartFile file
    );
}

