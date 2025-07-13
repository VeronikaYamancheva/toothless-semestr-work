package ru.itis.vhsroni.dentist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.vhsroni.dentist.data.entity.DentistSpecialization;

import java.util.UUID;

public interface DentistSpecializationRepository extends JpaRepository<DentistSpecialization, UUID> {
}
