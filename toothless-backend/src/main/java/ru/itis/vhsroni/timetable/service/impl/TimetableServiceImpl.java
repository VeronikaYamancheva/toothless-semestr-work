package ru.itis.vhsroni.timetable.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.appointment.data.entity.Appointment;
import ru.itis.vhsroni.appointment.repository.AppointmentRepository;
import ru.itis.vhsroni.config.property.AppConfigProperties;
import ru.itis.vhsroni.timetable.data.dto.response.CalendarificResponse;
import ru.itis.vhsroni.timetable.data.dto.enums.DayType;
import ru.itis.vhsroni.timetable.data.dto.response.TimetableResponse;
import ru.itis.vhsroni.timetable.service.HolidaysService;
import ru.itis.vhsroni.timetable.service.TimetableService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimetableServiceImpl implements TimetableService {

    private final AppConfigProperties appConfig;
    private final HolidaysService holidaysService;
    private final AppointmentRepository appointmentRepository;

    @Override
    public TimetableResponse getTimetableByDateRange(UUID dentistId, LocalDate beginDate, LocalDate endDate) {
        List<CalendarificResponse.Holiday> calendarificHolidays = holidaysService.getHolidaysInRussiaInRange(beginDate, endDate);
        Map<LocalDate, CalendarificResponse.Holiday> holidaysMap = calendarificHolidays.stream()
                .collect(Collectors.toMap(
                        holiday -> holidaysService.parseHolidayDate(holiday.getDate().getIso()),
                        holiday -> holiday
                ));
        log.debug("This date range holidays: {}", holidaysMap.keySet());
        List<Appointment> appointments = appointmentRepository.findAllByDentist_IdAndDateBetween(dentistId, beginDate, endDate);
        Map<LocalDate, List<Appointment>> appointmentsByDate = appointments.stream()
                .collect(Collectors.groupingBy(Appointment::getDate));
        List<TimetableResponse.TimetableDay> allDays = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        for (LocalDate date = beginDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            String dayOfWeekName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY || holidaysMap.containsKey(date)) {
                String holidayName = date.getDayOfWeek() == DayOfWeek.SUNDAY
                        ? "Weekend day"
                        : holidaysMap.get(date).getName();
                allDays.add(TimetableResponse.TimetableDay.builder()
                        .dayType(DayType.HOLIDAY)
                        .date(date)
                        .dayOfWeek(dayOfWeekName)
                        .holidayResponse(TimetableResponse.HolidayResponse.builder()
                                .name(holidayName)
                                .build())
                        .build());
            } else {
                List<Appointment> dayAppointments = appointmentsByDate.getOrDefault(date, Collections.emptyList());
                Map<LocalTime, TimetableResponse.WorkDayResponse.TimeSlot> busySlots = dayAppointments.stream()
                        .collect(Collectors.toMap(
                                Appointment::getBeginTime,
                                appointment -> TimetableResponse.WorkDayResponse.TimeSlot.builder()
                                        .beginTime(appointment.getBeginTime())
                                        .endTime(appointment.getEndTime())
                                        .isLunch(false)
                                        .isAvailable(false)
                                        .build()
                        ));
                List<TimetableResponse.WorkDayResponse.TimeSlot> allSlots = new ArrayList<>();
                LocalTime slotStart = appConfig.workDayBeginTime();
                LocalTime slotEnd = appConfig.workDayEndTime();
                LocalTime lunchStart = appConfig.lunchBeginTime();
                LocalTime lunchEnd = appConfig.lunchEndTime();
                while (slotStart.isBefore(slotEnd)) {
                    LocalTime next = slotStart.plusHours(1);
                    boolean isLunch = !slotStart.isBefore(lunchStart) && slotStart.isBefore(lunchEnd);
                    boolean isPastTime = date.equals(today) && slotStart.isBefore(now);

                    if (busySlots.containsKey(slotStart)) {
                        allSlots.add(busySlots.get(slotStart));
                    } else {
                        allSlots.add(TimetableResponse.WorkDayResponse.TimeSlot.builder()
                                .beginTime(slotStart)
                                .endTime(next)
                                .isLunch(isLunch)
                                .isAvailable(!isLunch && !isPastTime)
                                .build());
                    }
                    slotStart = next;
                }
                allDays.add(TimetableResponse.TimetableDay.builder()
                        .dayType(DayType.WORKDAY)
                        .date(date)
                        .dayOfWeek(dayOfWeekName)
                        .holidayResponse(null)
                        .workDayResponse(TimetableResponse.WorkDayResponse.builder()
                                .timeSlots(allSlots)
                                .build())
                        .build());
            }
        }
        allDays.sort(Comparator.comparing(TimetableResponse.TimetableDay::date));
        return TimetableResponse.builder().days(allDays).build();
    }
}
