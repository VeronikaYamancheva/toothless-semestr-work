package ru.itis.vhsroni.dentist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.vhsroni.dentist.data.entity.Dentist;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DentistRepository extends JpaRepository<Dentist, UUID> {

    Optional<Dentist> findByUser_UserId(UUID userId);

    @Query(value = "SELECT * FROM dentist LIMIT :limit", nativeQuery = true)
    List<Dentist> findLimitedDentists(@Param("limit") int limit);

    @Query("""
    SELECT d
    FROM Dentist d
    JOIN d.user u
    WHERE d.specialization IN (
        SELECT s FROM DentistSpecialization s
        JOIN s.procedures p
        WHERE p.procedureId = :procedureId
    )
    ORDER BY u.lastName, u.firstName
    """)
    List<Dentist> findDentistsByProcedure(@Param("procedureId") UUID procedureId);
}
