package ru.itis.vhsroni.timetable.data.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalendarificResponse {

    private Meta meta;
    private Response response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meta {
        private int code;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        private List<Holiday> holidays;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Holiday {
        private String name;
        private String description;
        private Date date;
        private List<String> type;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Date {
        private String iso;
        private DateTime datetime;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DateTime {
        private int year;
        private int month;
        private int day;
    }
}

