package ru.itis.vhsroni.profile.service;

import ru.itis.vhsroni.profile.data.dto.request.UpdateBaseProfileInfoRequest;
import ru.itis.vhsroni.profile.data.dto.request.UpdatePasswordRequest;

import java.util.UUID;

public interface BaseProfileService {

    void updateBaseProfileInfo(UUID userId, UpdateBaseProfileInfoRequest updatebaseProfileInfoRequest);

    void updatePassword(UUID userId, UpdatePasswordRequest updatePasswordRequest);
}
