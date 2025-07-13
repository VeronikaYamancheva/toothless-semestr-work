package ru.itis.vhsroni.appointment.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.vhsroni.appointment.data.entity.AppointmentForm;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public class AppointmentFormJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public AppointmentForm save(AppointmentForm form) {
        if (form.getFormId() == null) {
            entityManager.persist(form);
            return form;
        } else {
            return entityManager.merge(form);
        }
    }

    public Optional<AppointmentForm> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(AppointmentForm.class, id));
    }

    public List<AppointmentForm> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AppointmentForm> cq = cb.createQuery(AppointmentForm.class);
        Root<AppointmentForm> root = cq.from(AppointmentForm.class);
        cq.select(root);
        return entityManager.createQuery(cq).getResultList();
    }

    public boolean existsById(UUID id) {
        return findById(id).isPresent();
    }

    public long count() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(AppointmentForm.class)));
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Transactional
    public void deleteById(UUID id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Transactional
    public void delete(AppointmentForm form) {
        entityManager.remove(entityManager.contains(form) ? form : entityManager.merge(form));
    }

    @Transactional
    public void deleteAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AppointmentForm> cq = cb.createQuery(AppointmentForm.class);
        cq.from(AppointmentForm.class);
        entityManager.createQuery(cq).getResultList().forEach(entityManager::remove);
    }

    public Optional<AppointmentForm> findByAppointmentId(UUID appointmentId) {
        TypedQuery<AppointmentForm> query = entityManager.createQuery(
                "SELECT af FROM AppointmentForm af WHERE af.appointment.appointmentId = :appointmentId",
                AppointmentForm.class);
        query.setParameter("appointmentId", appointmentId);
        return query.getResultStream().findFirst();
    }
}