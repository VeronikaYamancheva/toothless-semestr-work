package ru.itis.vhsroni.timetable.service;

import ru.itis.vhsroni.timetable.data.dto.response.TimetableResponse;

import java.time.LocalDate;
import java.util.UUID;

public interface TimetableService {

    TimetableResponse getTimetableByDateRange(UUID dentistId, LocalDate beginDate, LocalDate endDate);
}
