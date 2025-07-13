package ru.itis.vhsroni.profile.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.auth.service.UserCredentialsService;
import ru.itis.vhsroni.auth.service.UserDataService;
import ru.itis.vhsroni.dentist.data.entity.Dentist;
import ru.itis.vhsroni.dentist.data.entity.DentistSpecialization;
import ru.itis.vhsroni.dentist.service.DentistService;
import ru.itis.vhsroni.profile.data.dto.inner.BaseProfileInfo;
import ru.itis.vhsroni.profile.data.dto.inner.DentistProfileInfo;
import ru.itis.vhsroni.profile.data.dto.inner.UserCredentialsProfileInfo;
import ru.itis.vhsroni.profile.data.dto.request.UpdateDentistProfileInfoRequest;
import ru.itis.vhsroni.profile.data.dto.response.DentistProfileResponse;
import ru.itis.vhsroni.profile.data.enums.ProfileType;
import ru.itis.vhsroni.profile.service.ProfileService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DentistProfileServiceImpl implements ProfileService<DentistProfileResponse> {

    private final DentistService dentistService;

    private final UserDataService userDataService;

    private final UserCredentialsService userCredentialsService;

    public DentistProfileResponse getProfile (UUID userId) {
        BaseProfileInfo baseProfileInfo = userDataService.getBaseProfileInfoByUserId(userId);
        UserCredentialsProfileInfo userCredentialsProfileInfo = userCredentialsService.getUserCredentialsProfileInfo(userId);
        DentistProfileInfo dentistProfileInfo = dentistService.getDentistProfileInfoByUserId(userId);
        return DentistProfileResponse.builder()
                .firstName(baseProfileInfo.firstName())
                .lastName(baseProfileInfo.lastName())
                .middleName(baseProfileInfo.middleName())
                .birthDate(baseProfileInfo.birthDate())
                .phoneNumber(baseProfileInfo.phoneNumber())
                .email(baseProfileInfo.email())
                .isApproved(baseProfileInfo.adminApproval())
                .specialization(dentistProfileInfo.specialization())
                .education(dentistProfileInfo.education())
                .experience(dentistProfileInfo.experience())
                .about(dentistProfileInfo.about())
                .authProviders(userCredentialsProfileInfo.authProviders())
                .profileType(ProfileType.DENTIST)
                .build();
    }

    public void updateDentistProfileInfo(UUID userId, UpdateDentistProfileInfoRequest dentistUpdateProfileRequest) {
        dentistService.updateDentistProfileInfo(userId, dentistUpdateProfileRequest);
    }
}
