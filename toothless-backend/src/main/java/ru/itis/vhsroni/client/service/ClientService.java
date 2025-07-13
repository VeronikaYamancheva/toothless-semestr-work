package ru.itis.vhsroni.client.service;


import ru.itis.vhsroni.profile.data.dto.inner.ClientProfileInfo;
import ru.itis.vhsroni.profile.data.dto.request.UpdateClientProfileInfoRequest;

import java.util.UUID;

public interface ClientService {
    void createClientByUserEmail(String email);

    void createClientByUserId(UUID userId);

    ClientProfileInfo getClientProfileInfoByUserId(UUID userId);

    void updateClientProfileInfo (UUID userId, UpdateClientProfileInfoRequest updateClientProfileInfoRequest);

    void deleteClientByUserId(UUID userId);
}
