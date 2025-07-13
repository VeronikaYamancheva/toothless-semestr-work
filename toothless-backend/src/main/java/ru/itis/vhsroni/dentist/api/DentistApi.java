package ru.itis.vhsroni.dentist.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itis.vhsroni.dentist.data.dto.response.DentistDetailedResponse;
import ru.itis.vhsroni.dentist.data.dto.response.DentistShortResponse;
import ru.itis.vhsroni.dentist.data.dto.response.DentistSpecializationResponse;
import ru.itis.vhsroni.error.dto.ApiErrorResponse;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Dentist API",
        description = "Endpoints for managing and retrieving dentist information"
)
@RequestMapping(path = "/api/v1/dentists", produces = "application/json")
public interface DentistApi {

    @Operation(
            summary = "Get all dentists (paginated)",
            description = "Returns a paginated list of all dentists with short information",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Dentists retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class)
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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<DentistShortResponse> getAllDentist(
            @ParameterObject
            Pageable pageable
    );

    @Operation(
            summary = "Get dentist by ID",
            description = "Returns detailed information about a specific dentist",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Dentist details retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DentistDetailedResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Dentist not found",
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
    @GetMapping("/{dentistId}")
    @ResponseStatus(HttpStatus.OK)
    DentistDetailedResponse getDentistById(
            @Parameter(
                    description = "ID of the dentist",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("dentistId") UUID dentistId
    );

    @Operation(
            summary = "Get dentists for main page (demo)",
            description = "Returns a short list (length - property value) of dentists for display on the main page",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Dentists retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DentistShortResponse[].class)
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
    List<DentistShortResponse> getDentistsDemoForMainPage();

    @Operation(
            summary = "Get dentists by procedure ID",
            description = "Returns a list of dentists who perform the specified procedure",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Dentists retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DentistShortResponse[].class)
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
    @GetMapping("/procedure/{procedureId}")
    @ResponseStatus(HttpStatus.OK)
    List<DentistShortResponse> getDentistsByProcedureId(
            @Parameter(
                    description = "ID of the procedure",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("procedureId") UUID procedureId
    );

    @Operation(
            summary = "Get all specializations",
            description = "Returns a list of all available dentist specializations",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Specializations retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DentistSpecializationResponse[].class)
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
    @GetMapping("/specializations")
    @ResponseStatus(HttpStatus.OK)
    List<DentistSpecializationResponse> getAllSpecializations();
}
