package ru.itis.vhsroni.profile.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.vhsroni.auth.service.UserCredentialsService;
import ru.itis.vhsroni.auth.service.UserDataService;
import ru.itis.vhsroni.client.service.ClientService;
import ru.itis.vhsroni.profile.data.dto.inner.BaseProfileInfo;
import ru.itis.vhsroni.profile.data.dto.inner.ClientProfileInfo;
import ru.itis.vhsroni.profile.data.dto.inner.UserCredentialsProfileInfo;
import ru.itis.vhsroni.profile.data.dto.request.UpdateClientProfileInfoRequest;
import ru.itis.vhsroni.profile.data.dto.response.ClientProfileResponse;
import ru.itis.vhsroni.profile.data.enums.ProfileType;
import ru.itis.vhsroni.profile.service.ProfileService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientProfileServiceImpl implements ProfileService<ClientProfileResponse> {

    private final UserDataService userDataService;

    private final ClientService clientService;

    private final UserCredentialsService userCredentialsService;

    @Override
    @Transactional
    public ClientProfileResponse getProfile(UUID userId) {
        BaseProfileInfo baseProfileInfo = userDataService.getBaseProfileInfoByUserId(userId);
        ClientProfileInfo clientProfileInfo = clientService.getClientProfileInfoByUserId(userId);
        UserCredentialsProfileInfo userCredentialsProfileInfo = userCredentialsService.getUserCredentialsProfileInfo(userId);
        return ClientProfileResponse.builder()
                .firstName(baseProfileInfo.firstName())
                .lastName(baseProfileInfo.lastName())
                .middleName(baseProfileInfo.middleName())
                .birthDate(baseProfileInfo.birthDate())
                .phoneNumber(baseProfileInfo.phoneNumber())
                .email(baseProfileInfo.email())
                .isApproved(baseProfileInfo.adminApproval())
                .passportFullNumber(clientProfileInfo.passportFullNumber())
                .passportIssueDate(clientProfileInfo.passportIssueDate())
                .passportIssuedBy(clientProfileInfo.passportIssuedBy())
                .passportDivisionCode(clientProfileInfo.passportDivisionCode())
                .policyNumber(clientProfileInfo.policyNumber())
                .snils(clientProfileInfo.snils())
                .authProviders(userCredentialsProfileInfo.authProviders())
                .profileType(ProfileType.CLIENT)
                .build();
    }

    public void updateClientProfile(UUID userId, UpdateClientProfileInfoRequest clientUpdateProfileRequest) {
        clientService.updateClientProfileInfo(userId, clientUpdateProfileRequest);
    }
}
