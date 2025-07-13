package ru.itis.vhsroni.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.vhsroni.config.property.AppConfigProperties;
import ru.itis.vhsroni.timetable.api.TimetableApi;
import ru.itis.vhsroni.timetable.data.dto.response.TimetableResponse;
import ru.itis.vhsroni.timetable.service.TimetableService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TimetableController implements TimetableApi {

    private final AppConfigProperties appConfig;

    private final TimetableService timetableService;

    @Override
    public TimetableResponse getTimeTableByDentistId(UUID dentistId) {
        Duration timetableVisibility = appConfig.timetableVisibility();
        LocalDate beginDate = LocalDate.now();
        LocalDate endDate = beginDate.plusDays(timetableVisibility.toDays() - 1);
        return timetableService.getTimetableByDateRange(dentistId, beginDate, endDate);
    }
}
