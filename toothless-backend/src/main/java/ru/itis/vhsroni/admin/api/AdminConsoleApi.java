package ru.itis.vhsroni.admin.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.vhsroni.admin.data.dto.response.AdminConsoleDentistResponse;
import ru.itis.vhsroni.admin.data.dto.response.AdminConsoleUserResponse;
import ru.itis.vhsroni.auth.data.principal.UnifiedAuthPrincipal;
import ru.itis.vhsroni.error.dto.ApiErrorResponse;
import ru.itis.vhsroni.error.dto.OperationResponse;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Admin console API",
        description = """
                Endpoints for users with the roles of `MASTER` or `ADMIN`, which allow to get general information about \
                the political clinic, as well as manage the states of ordinary users.
                """)
@SecurityRequirement(name = "BearerAuth")
@RequestMapping(path = "/api/v1/admin", produces = "application/json")
public interface AdminConsoleApi {

    @Operation(
            summary = "Get a list of all users with info about their roles and state",
            description = """
                    Returns a paginated list of users with their roles, are they an administrator and account state. \
                    Available only for administrators (`ADMIN`) and masters (`MASTER`). Only the MASTER can request data \
                    about whether the user has the admin role, so if the ADMIN user makes a request, he receives null values.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Successfully getting the page of users",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AdminConsoleUserResponse[].class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401", description = "Unauthorized user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403", description = "Access is denied because the user does not have the appropriate role.",
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MASTER')")
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    Page<AdminConsoleUserResponse> getAllUsersForAdmin(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal,

            @ParameterObject
            Pageable pageable
    );

    @Operation(
            summary = "Updating the admin role for a user by a user with the master role",
            description = """
                    Only users with the `MASTER` role can grant/take the `ADMIN` role from the user.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Role was successfully updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401", description = "Unauthorized user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403", description = "Access is denied because the user does not have the appropriate role.",
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
    @PreAuthorize("hasRole('MASTER')")
    @PatchMapping("/users/{userId}/admin")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updateAdminRole(
            @Parameter(
                    description = "User ID",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("userId") UUID userId,

            @Parameter(
                    description = "Flag indicating whether the user is an administrator",
                    required = true,
                    schema = @Schema(type = "boolean", defaultValue = "false")
            )
            @RequestParam("isAdmin") boolean isAdmin
    );

    @Operation(
            summary = "Updating the BANNED state for a user by a user with the MASTER/ADMIN role",
            description = """
                    Only users with the `MASTER`/`ADMIN` role can grant/take the `BANNED` state from the user.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "State was successfully updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401", description = "Unauthorized user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403", description = "Access is denied because the user does not have the appropriate role.",
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MASTER')")
    @PatchMapping("/users/{userId}/ban")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updateBannedStatus(
            @Parameter(
                    description = "User ID",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("userId") UUID userId,

            @Parameter(
                    description = "Flag indicating whether the user has BANNED state",
                    required = true,
                    schema = @Schema(type = "boolean", defaultValue = "false")
            )
            @RequestParam("isBanned") boolean isBanned
    );

    @Operation(
            summary = "Getting a list of all unconfirmed dentists",
            description = """
                    To get the dentist role, you need to be confirmed by the administrator, who can accept/reject the \
                    application.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "State was successfully updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AdminConsoleDentistResponse[].class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401", description = "Unauthorized user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403", description = "Access is denied because the user does not have the appropriate role.",
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MASTER')")
    @GetMapping("/dentist")
    @ResponseStatus(HttpStatus.OK)
    List<AdminConsoleDentistResponse> getAllNotApprovedDentist();

    @Operation(
            summary = "Dentist's confirmation",
            description = """
                    Confirms the user who registered as a dentist, gives him the DENTIST role and creates a dentist account.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "State was successfully updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401", description = "Unauthorized user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403", description = "Access is denied because the user does not have the appropriate role.",
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MASTER')")
    @PostMapping("/dentist/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    OperationResponse approveDentist(
            @Parameter(
                    description = "User ID",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("userId") UUID userId,

            @Parameter(
                    description = "Flag indicating whether the user is approved by administrator",
                    required = true,
                    schema = @Schema(type = "boolean", defaultValue = "false")
            )
            @RequestParam("isApproved") boolean isApproved
    );
}
