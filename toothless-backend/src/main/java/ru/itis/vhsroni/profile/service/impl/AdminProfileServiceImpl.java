package ru.itis.vhsroni.profile.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.service.UserCredentialsService;
import ru.itis.vhsroni.auth.service.UserDataService;
import ru.itis.vhsroni.profile.data.dto.inner.BaseProfileInfo;
import ru.itis.vhsroni.profile.data.dto.inner.UserCredentialsProfileInfo;
import ru.itis.vhsroni.profile.data.dto.response.AdminProfileResponse;
import ru.itis.vhsroni.profile.data.enums.ProfileType;
import ru.itis.vhsroni.profile.service.ProfileService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminProfileServiceImpl implements ProfileService<AdminProfileResponse> {

    private final UserDataService userDataService;

    private final UserCredentialsService userCredentialsService;

    @Override
    public AdminProfileResponse getProfile(UUID userId) {
        BaseProfileInfo baseProfileInfo = userDataService.getBaseProfileInfoByUserId(userId);
        UserCredentialsProfileInfo userCredentialsProfileInfo = userCredentialsService.getUserCredentialsProfileInfo(userId);
        return AdminProfileResponse.builder()
                .firstName(baseProfileInfo.firstName())
                .lastName(baseProfileInfo.lastName())
                .middleName(baseProfileInfo.middleName())
                .birthDate(baseProfileInfo.birthDate())
                .phoneNumber(baseProfileInfo.phoneNumber())
                .email(baseProfileInfo.email())
                .isApproved(baseProfileInfo.adminApproval())
                .authProviders(userCredentialsProfileInfo.authProviders())
                .profileType(ProfileType.ADMIN)
                .build();
    }
}
