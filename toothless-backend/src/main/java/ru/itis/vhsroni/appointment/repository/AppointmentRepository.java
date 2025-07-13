package ru.itis.vhsroni.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.vhsroni.appointment.data.entity.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findAllByDentist_IdAndDateBetween(
            UUID dentistId,
            LocalDate beginDate,
            LocalDate endDate
    );

    List<Appointment> findAllByDentist_IdAndDateAndBeginTimeAndEndTime(
            UUID dentistId,
            LocalDate date,
            LocalTime beginTime,
            LocalTime endTime
    );

    List<Appointment> findAllByClient_UserIdAndDateAndBeginTimeAndEndTime(
            UUID clientId,
            LocalDate date,
            LocalTime beginTime,
            LocalTime endTime
    );

    @Query(
            """
            SELECT a FROM Appointment a
            WHERE a.date < :date AND a.client.userId = :clientId
            """
    )
    List<Appointment> findAllPastByClientAndDate(
            @Param("clientId") UUID clientId,
            @Param("date") LocalDate date
    );

    @Query(
            """
            SELECT a FROM Appointment a
            WHERE a.date >= :date AND a.client.userId = :clientId
            """
    )
    List<Appointment> findAllUpcomingByClientAndDate(
            @Param("clientId") UUID clientId,
            @Param("date") LocalDate date
    );

    @Query(
            """
            SELECT a FROM Appointment a
            WHERE a.date < :date AND a.dentist.id = :dentistId
            """
    )
    List<Appointment> findAllPastByDentistAndDate(
            @Param("dentistId") UUID dentistId,
            @Param("date") LocalDate date
    );

    @Query(
            """
            SELECT a FROM Appointment a
            WHERE a.date >= :date AND a.dentist.id = :dentistId
            """
    )
    List<Appointment> findAllUpcomingByDentistAndDate(
            @Param("dentistId") UUID dentistId,
            @Param("date") LocalDate date
    );

}
