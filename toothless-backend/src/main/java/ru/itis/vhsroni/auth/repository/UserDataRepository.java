package ru.itis.vhsroni.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.itis.vhsroni.auth.data.entity.UserData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDataRepository extends JpaRepository<UserData, UUID>, PagingAndSortingRepository<UserData, UUID> {

    Optional<UserData> findByEmail(String email);

    Optional<UserData> findUserDataByUserId(UUID userId);

    @Query(
            """
            SELECT u FROM UserData u
            WHERE ru.itis.vhsroni.auth.data.enums.Role.MASTER NOT MEMBER OF u.roles
            AND u.state != ru.itis.vhsroni.auth.data.enums.State.DELETED
        """)
    Page<UserData> findAllActiveUsersWithoutMasterRole(Pageable pageable);

    @Query("SELECT u FROM UserData u WHERE u.adminApproval = false AND u.state = ru.itis.vhsroni.auth.data.enums.State.ACTIVE")
    List<UserData> findAllNotApprovedActiveUsers();

    boolean existsByEmail(String email);
}
