package ru.itis.vhsroni.timetable.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.vhsroni.error.dto.ApiErrorResponse;
import ru.itis.vhsroni.timetable.data.dto.response.TimetableResponse;

import java.util.UUID;

@Tag(name = "Timetable API",
        description = "Endpoints for managing dentist timetables")
@RequestMapping(path = "/api/v1/timetable", produces = "application/json")
public interface TimetableApi {

    @Operation(
            summary = "Get dentist timetable",
            description = "Retrieves the timetable for a specific dentist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Timetable retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TimetableResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Dentist not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )

    @GetMapping("/{dentistId}")
    TimetableResponse getTimeTableByDentistId(
            @Parameter(
                    description = "ID of the dentist",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable("dentistId") UUID dentistId
    );
}