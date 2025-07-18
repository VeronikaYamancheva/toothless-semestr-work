package ru.itis.vhsroni.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.vhsroni.client.data.entity.Client;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findByUser_UserId(UUID userId);

    void deleteByUser_UserId(UUID userId);
}
