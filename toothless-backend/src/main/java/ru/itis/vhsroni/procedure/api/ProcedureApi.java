package ru.itis.vhsroni.procedure.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.vhsroni.error.dto.ApiErrorResponse;
import ru.itis.vhsroni.error.dto.OperationResponse;
import ru.itis.vhsroni.error.dto.ValidationErrorResponse;
import ru.itis.vhsroni.procedure.data.dto.request.CreateProcedureRequest;
import ru.itis.vhsroni.procedure.data.dto.request.UpdateProcedureRequest;
import ru.itis.vhsroni.procedure.data.dto.response.ProcedureCreationResponse;
import ru.itis.vhsroni.procedure.data.dto.response.ProcedureDetailedResponse;
import ru.itis.vhsroni.procedure.data.dto.response.ProcedureShortResponse;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Procedure API",
        description = "Endpoints for managing and retrieving dental procedures information"
)
@RequestMapping(path = "/api/v1/procedures", produces = "application/json; charset=utf-8")
public interface ProcedureApi {

    @Operation(
            summary = "Get all procedures (paginated)",
            description = "Returns a paginated list of all dental procedures with short information",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Procedures retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class)
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
    Page<ProcedureShortResponse> getAllProcedures(
            @ParameterObject
            Pageable pageable
    );

    @Operation(
            summary = "Get procedure by ID",
            description = "Returns detailed information about a specific dental procedure",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Procedure details retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProcedureDetailedResponse.class)
                            )),
                    @ApiResponse(
                            responseCode = "404", description = "Procedure not found",
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
    @GetMapping("/{procedureId}")
    @ResponseStatus(HttpStatus.OK)
    ProcedureDetailedResponse getProcedureById(
            @Parameter(
                    description = "ID of the procedure to retrieve",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable("procedureId") UUID procedureId
    );

    @Operation(
            summary = "Get procedures for main page (demo)",
            description = "Returns a short list of procedures for display on the main page",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Procedures retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProcedureShortResponse[].class)
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
    @GetMapping("/demo")
    @ResponseStatus(HttpStatus.OK)
    List<ProcedureShortResponse> getProceduresDemoForMainPage();

    @Operation(
            summary = "Create new procedure",
            description = "Create new procedure by ADMIN/MASTER user",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Procedures created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProcedureCreationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Invalid inputData",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
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
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MASTER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProcedureCreationResponse createProcedure(
            @Parameter(description = "Request for procedure creation")
            @Valid @RequestBody CreateProcedureRequest createProcedureRequest
    );

    @Operation(
            summary = "Update procedure",
            description = "Create new procedure by ADMIN/MASTER user",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Procedures created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProcedureCreationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Invalid inputData",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
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
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MASTER')")
    @PatchMapping("/{procedureId}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updateProcedure(
            @Parameter(
                    description = "ID of the procedure to update",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable("procedureId") UUID procedureId,
            @Valid @RequestBody UpdateProcedureRequest updateProcedureRequest
    );

    @Operation(
            summary = "Update procedure",
            description = "Create new procedure by ADMIN/MASTER user",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Procedures created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProcedureCreationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Invalid inputData",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
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
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MASTER')")
    @DeleteMapping("/{procedureId}")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse deleteProcedure(
            @Parameter(
                    description = "ID of the procedure to delete",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable("procedureId") UUID procedureId
    );


}
