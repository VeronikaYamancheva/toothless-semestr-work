package ru.itis.vhsroni.timetable.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.itis.vhsroni.config.property.CalendarificConfigProperties;
import ru.itis.vhsroni.timetable.data.dto.response.CalendarificResponse;
import ru.itis.vhsroni.timetable.service.HolidaysService;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarificHolidayServiceImpl implements HolidaysService {

    private final CalendarificConfigProperties calendarificConfig;

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://calendarific.com/api/v2/holidays";

    public List<CalendarificResponse.Holiday> getHolidaysInRussiaInRange(LocalDate start, LocalDate end) {
        List<CalendarificResponse.Holiday> result = new ArrayList<>();
        for (int year = start.getYear(); year <= end.getYear(); year++) {
            String urlPattern = "%s?api_key=%s&country=%s&year=%s";
            String url = urlPattern.formatted(
                    BASE_URL,
                    calendarificConfig.apiKey(),
                    "RU",
                    year
            );
            log.debug("Calendarific API url: {}", url);

            ResponseEntity<CalendarificResponse> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    CalendarificResponse.class
            );
            log.debug("API response status: {}", responseEntity.getStatusCode());
            CalendarificResponse response = responseEntity.getBody();
            if (response != null && response.getResponse() != null && response.getResponse().getHolidays() != null) {
                for (CalendarificResponse.Holiday holiday : response.getResponse().getHolidays()) {
                    LocalDate holidayDate = parseHolidayDate(holiday.getDate().getIso());
                    if (!holidayDate.isBefore(start) && !holidayDate.isAfter(end)) {
                        result.add(holiday);
                    }
                }
            }
        }
        return result;
    }

    public LocalDate parseHolidayDate(String isoDate) {
        try {
            return OffsetDateTime.parse(isoDate).toLocalDate();
        } catch (Exception e) {
            return LocalDate.parse(isoDate);
        }
    }
}
