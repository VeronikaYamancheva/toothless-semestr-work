package ru.itis.vhsroni.procedure.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.itis.vhsroni.procedure.data.entity.Procedure;

import java.util.List;
import java.util.UUID;

public interface ProcedureRepository extends PagingAndSortingRepository<Procedure, UUID>, JpaRepository<Procedure, UUID> {

    @Query(value = "SELECT * FROM procedure WHERE is_active = true LIMIT :limit", nativeQuery = true)
    List<Procedure> findLimitedProcedures(@Param("limit") int limit);

    Page<Procedure> findAllByActiveTrue(Pageable pageable);

    List<Procedure> findAllByActiveTrue();

    Procedure findByActiveTrueAndProcedureId(UUID procedureId);
}
