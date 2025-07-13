package ru.itis.vhsroni.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.vhsroni.auth.data.entity.UserCredentials;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.data.enums.AuthProvider;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, UUID> {

    List<UserCredentials> findByUserData(UserData userData);

    @Query("""
            SELECT c FROM UserCredentials c
            WHERE c.userData.state != ru.itis.vhsroni.auth.data.enums.State.DELETED
            AND c.providerKey = :providerKey
            AND c.providerType = :providerType
            """)
    Optional<UserCredentials> findActiveByProviderKeyAndProviderType(
            @Param("providerKey") String providerKey,
            @Param("providerType") AuthProvider providerType
    );

    List<UserCredentials> findAllByUserData_UserId(UUID userId);

}
