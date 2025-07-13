package ru.itis.vhsroni.profile.api;

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
import ru.itis.vhsroni.error.dto.ApiErrorResponse;
import ru.itis.vhsroni.error.dto.OperationResponse;
import ru.itis.vhsroni.error.dto.ValidationErrorResponse;
import ru.itis.vhsroni.profile.data.dto.request.UpdateBaseProfileInfoRequest;
import ru.itis.vhsroni.profile.data.dto.request.UpdateClientProfileInfoRequest;
import ru.itis.vhsroni.profile.data.dto.request.UpdateDentistProfileInfoRequest;
import ru.itis.vhsroni.profile.data.dto.request.UpdatePasswordRequest;
import ru.itis.vhsroni.profile.data.dto.response.AdminProfileResponse;
import ru.itis.vhsroni.profile.data.dto.response.BaseProfileResponse;
import ru.itis.vhsroni.profile.data.dto.response.ClientProfileResponse;
import ru.itis.vhsroni.profile.data.dto.response.DentistProfileResponse;

@Tag(
        name = "Profile API",
        description = "Endpoints for managing user profile information"
)
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api/v1/profile", produces = "application/json")
public interface ProfileApi {
    @Operation(
            summary = "Get current user profile",
            description = "Returns profile information based on user role (ADMIN, DENTIST or CLIENT)",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Profile retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(oneOf = {
                                            AdminProfileResponse.class,
                                            DentistProfileResponse.class,
                                            ClientProfileResponse.class
                                    })
                            )
                    ),
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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    BaseProfileResponse getProfile(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Update base profile information",
            description = "Updates common profile fields for all user types",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Profile updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Invalid input data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401", description = "Unauthorized",
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
    @PutMapping("/base")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updateBaseProfileInformation(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal,

            @Parameter(description = "Base profile update data", required = true)
            @Valid @RequestBody UpdateBaseProfileInfoRequest baseUpdateProfileRequest
    );

    @Operation(
            summary = "Update dentist profile information",
            description = "Updates dentist-specific profile fields",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Profile updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Invalid input data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401", description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )),
                    @ApiResponse(
                            responseCode = "403", description = "Forbidden (not a dentist)",
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
    @PreAuthorize("hasRole('DENTIST')")
    @PutMapping("/dentist")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updateDentistProfileInformation(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal,

            @Parameter(description = "Dentist profile update data", required = true)
            @Valid @RequestBody UpdateDentistProfileInfoRequest dentistUpdateProfileRequest
    );

    @Operation(
            summary = "Update client profile information",
            description = "Updates client-specific profile fields",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Profile updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Invalid input data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
                            )),
                    @ApiResponse(
                            responseCode = "401", description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )),
                    @ApiResponse(
                            responseCode = "403", description = "Forbidden (not a client)",
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
    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/client")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updateClientProfileInformation(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal,

            @Parameter(description = "Client profile update data", required = true)
            @Valid @RequestBody UpdateClientProfileInfoRequest clientUpdateProfileRequest
    );

    @Operation(
            summary = "Update password",
            description = "Changes user password after verifying old password",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Password updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )),
                    @ApiResponse(
                            responseCode = "400", description = "Invalid input data or old password incorrect",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
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
                            ))
            }
    )
    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updatePassword(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal,

            @Parameter(description = "Password update data", required = true)
            @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest
    );
}

