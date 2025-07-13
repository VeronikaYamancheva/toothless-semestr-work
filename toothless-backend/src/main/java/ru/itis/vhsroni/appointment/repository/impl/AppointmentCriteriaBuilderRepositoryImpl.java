package ru.itis.vhsroni.appointment.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ru.itis.vhsroni.appointment.data.entity.Appointment;
import ru.itis.vhsroni.appointment.repository.AppointmentCriteriaBuilderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AppointmentCriteriaBuilderRepositoryImpl implements AppointmentCriteriaBuilderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Appointment> findAppointmentsByDateRange(LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> query = cb.createQuery(Appointment.class);
        Root<Appointment> appointment = query.from(Appointment.class);

        appointment.fetch("client", JoinType.LEFT);
        appointment.fetch("dentist", JoinType.LEFT);

        if (startDate != null || endDate != null) {
            List<Predicate> predicates = new ArrayList<>();
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(appointment.get("date"), startDate));
            }
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(appointment.get("date"), endDate));
            }
            query.where(predicates.toArray(new Predicate[0]));
        }
        query.orderBy(
                cb.asc(appointment.get("date")),
                cb.asc(appointment.get("beginTime"))
        );
        return entityManager.createQuery(query).getResultList();
    }
}