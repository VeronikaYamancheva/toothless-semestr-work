package ru.itis.vhsroni.profile.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.auth.service.UserCredentialsService;
import ru.itis.vhsroni.auth.service.UserDataService;
import ru.itis.vhsroni.profile.data.dto.request.UpdateBaseProfileInfoRequest;
import ru.itis.vhsroni.profile.data.dto.request.UpdatePasswordRequest;
import ru.itis.vhsroni.profile.service.BaseProfileService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseProfileServiceImpl implements BaseProfileService {

    private final UserDataService userDataService;

    private final UserCredentialsService userCredentialsService;

    @Override
    public void updateBaseProfileInfo(UUID userId, UpdateBaseProfileInfoRequest updateBaseProfileRequest) {
        userDataService.updateBaseProfileInfo(userId, updateBaseProfileRequest);
    }

    @Override
    public void updatePassword(UUID userId, UpdatePasswordRequest updatePasswordRequest) {
        userCredentialsService.updateLocalPassword(userId, updatePasswordRequest);
    }
}
