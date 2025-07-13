package ru.itis.vhsroni.appointment.repository;

import ru.itis.vhsroni.appointment.data.entity.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentCriteriaBuilderRepository {

    List<Appointment> findAppointmentsByDateRange(LocalDate startDate, LocalDate endDate);
}
