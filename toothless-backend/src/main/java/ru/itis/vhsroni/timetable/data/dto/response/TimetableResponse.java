package ru.itis.vhsroni.timetable.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.itis.vhsroni.timetable.data.dto.enums.DayType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Schema(description = "Dentist's timetable response")
@Builder
public record TimetableResponse (
        @Schema(description = "List of days in the timetable")
        List<TimetableDay> days
){
    @Schema(description = "Timetable day information")
    @Builder
    public record TimetableDay(
            @Schema(description = "Type of day (WORKDAY/HOLIDAY)", example = "WORKDAY")
            DayType dayType,

            @Schema(description = "Date of the day", example = "2023-12-25")
            LocalDate date,

            @Schema(description = "Name of the day of week", example = "Monday")
            String dayOfWeek,

            @Schema(description = "Holiday details (present when dayType is HOLIDAY)", nullable = true)
            HolidayResponse holidayResponse,

            @Schema(description = "Work day details (present when dayType is WORKDAY)", nullable = true)
            WorkDayResponse workDayResponse
    ){}

    @Schema(description = "Holiday information")
    @Builder
    public record HolidayResponse(
            @Schema(description = "Name of the holiday", example = "New Year")
            String name
    ){}

    @Schema(description = "Work day information")
    @Builder
    public record WorkDayResponse(
            @Schema(description = "List of time slots for appointments")
            List<TimeSlot> timeSlots
    ){
        @Schema(description = "Time slot information")
        @Builder
        public record TimeSlot(
                @Schema(description = "Start time of the slot", example = "09:00:00")
                LocalTime beginTime,

                @Schema(description = "End time of the slot", example = "10:00:00")
                LocalTime endTime,

                @Schema(description = "Flag indicating if this is a lunch break", example = "false")
                boolean isLunch,

                @Schema(description = "Flag indicating if the slot is available for booking", example = "false")
                boolean isAvailable
        ){}
    }
}