package ru.itis.vhsroni.appointment.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.vhsroni.appointment.data.dto.request.CreateAppointmentRequest;
import ru.itis.vhsroni.appointment.data.dto.request.UpdateAppointmentFormRequest;
import ru.itis.vhsroni.appointment.data.dto.response.*;
import ru.itis.vhsroni.auth.data.principal.UnifiedAuthPrincipal;
import ru.itis.vhsroni.error.dto.ApiErrorResponse;
import ru.itis.vhsroni.error.dto.OperationResponse;
import ru.itis.vhsroni.error.dto.ValidationErrorResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Tag(
        name = "Appointment API",
        description = "API for managing user's appointments"
)
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api/v1/appointments", produces = "application/json")
public interface AppointmentApi {

    @Operation(
            summary = "Creates a new appointment",
            description = """
                    Creates a new dentist appointment based on the data selected in the timeline and user information \
                    obtained from authentication.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Appointment created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreateAppointmentResponse.class)
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
                            responseCode = "400",
                            description = """
                                    Invalid input data with ErrorCode:
                                    1. `INVALID_INPUT_DATA` - incorrect filling of the base CreateAppointmentResponse fields
                                    1. `PROCEDURE_NOT_FOUND` - the procedure that the user specified was not found
                                    2. `INCORRECT_TIME_SLOT` - the selected time slot is incorrect
                                    3. `APPOINTMENT_HAS_ALREADY_BOOKED` - this time slot is already booked
                                    4. `CLIENT_ALREADY_HAS_APPOINTMENT` - the client already has an appointment for this time
                                    5. `PAST_DATE` - you cannot select a date that has passed
                                    6. `PAST_TIME` - you cannot select the time that has passed today
                                    7. `DENTIST_NOT_FOUND` - the selected dentist was not found
                                    8. `LUNCH_TIME` - the selected time is the same as lunch time
                                    """,
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorResponse.class)
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateAppointmentResponse createAppointment(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal,

            @Parameter(description = "Data for creating a new appointment")
            @Valid @RequestBody CreateAppointmentRequest createAppointmentRequest
    );

    @Operation(
            summary = "Get all client's appointments which were in the past",
            description = """
                    Getting a list of all the current client's appointments that have already been completed.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Appointments have been successfully received",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientAppointmentResponse[].class)
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
                            responseCode = "500", description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }

    )
    @GetMapping("/client/past")
    @ResponseStatus(HttpStatus.OK)
    List<ClientAppointmentResponse> getClientPastAppointments(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Get all client's future appointments",
            description = """
                    Getting a list of all the current client's appointments that will be in future.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Appointments have been successfully received",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientAppointmentResponse[].class)
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
                            responseCode = "500", description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }

    )
    @GetMapping("/client/upcoming")
    @ResponseStatus(HttpStatus.OK)
    List<ClientAppointmentResponse> getClientUpcomingAppointments(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Getting all appointments in the date range",
            description = """
                    Getting a list of basic information about records in a date range that is dynamically filtered.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Appointments have been successfully received",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AdminAppointmentShortResponse[].class)
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
    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN')")
    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    List<AdminAppointmentShortResponse> getAppointmentsByDateRange(
            @Parameter(
                    description = "Appointments begin date",
                    example = "2024-01-01"
            )
            @RequestParam(value = "begin", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate beginDate,

            @Parameter(
                    description = "Appointments end date",
                    example = "2024-12-31"
            )
            @RequestParam(value = "end", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    );

    @Operation(
            summary = "Getting appointment detailed info",
            description = """
                    Getting detailed information about the appointment, including the contact details of the client and the doctor
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Appointment have been successfully received",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AdminAppointmentDetailedResponse.class)
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
                            responseCode = "404", description = "Appointment not found",
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
    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN')")
    @GetMapping("/{appointmentId}/admin")
    @ResponseStatus(HttpStatus.OK)
    AdminAppointmentDetailedResponse getAppointmentDetailedInfoForAdmin(
            @Parameter(
                    description = "Appointment ID",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("appointmentId") UUID appointmentId
    );

    @Operation(
            summary = "Get all dentist's appointments which were in the past",
            description = """
                    Getting a list of all the current dentist's appointments that have already been completed.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Appointments have been successfully received",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DentistAppointmentResponse[].class)
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
    @PreAuthorize("hasRole('DENTIST')")
    @GetMapping("/dentist/past")
    @ResponseStatus(HttpStatus.OK)
    List<DentistAppointmentResponse> getDentistPastAppointments(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Get all dentist's future appointments",
            description = """
                    Getting a list of all the current dentist's appointments that will be in future.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Appointments have been successfully received",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DentistAppointmentResponse[].class)
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
    @PreAuthorize("hasRole('DENTIST')")
    @GetMapping("/dentist/upcoming")
    @ResponseStatus(HttpStatus.OK)
    List<DentistAppointmentResponse> getDentistUpcomingAppointments(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UnifiedAuthPrincipal authPrincipal
    );

    @Operation(
            summary = "Getting appointment form",
            description = """
                    Receiving the appointment form - detailed information about the appointment, filled out by the doctor
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Appointment form have been successfully received",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AppointmentFormResponse.class)
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
                            responseCode = "404", description = "Appointment form not found",
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
    @GetMapping("/{appointmentId}/form")
    @ResponseStatus(HttpStatus.OK)
    AppointmentFormResponse getAppointmentFormByAppointmentId(
            @Parameter(
                    description = "Appointment ID",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("appointmentId") UUID appointmentId
    );

    @Operation(
            summary = "Update appointment form",
            description = """
                    Changing/filling out the registration form is done only by the doctor.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Appointments have been successfully received",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Operation.class)
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
                            responseCode = "404", description = "Appointment form not found",
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
    @PreAuthorize("hasRole('DENTIST')")
    @PutMapping("/{appointmentId}/form")
    @ResponseStatus(HttpStatus.OK)
    OperationResponse updateAppointmentForm(
            @Parameter(
                    description = "Appointment ID",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("appointmentId") UUID appointmentId,

            @Parameter(description = "Data for creating a new appointment")
            @Valid @RequestBody UpdateAppointmentFormRequest updateAppointmentFormRequest
    );
}
