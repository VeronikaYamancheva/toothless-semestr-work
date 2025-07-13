package ru.itis.vhsroni.timetable.service;

import ru.itis.vhsroni.timetable.data.dto.response.CalendarificResponse;

import java.time.LocalDate;
import java.util.List;

public interface HolidaysService {

    List<CalendarificResponse.Holiday> getHolidaysInRussiaInRange(LocalDate start, LocalDate end);

    LocalDate parseHolidayDate(String isoDate);
}
