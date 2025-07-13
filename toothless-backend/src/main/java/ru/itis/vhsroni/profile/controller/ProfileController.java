package ru.itis.vhsroni.profile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.vhsroni.auth.data.enums.Role;
import ru.itis.vhsroni.auth.data.principal.UnifiedAuthPrincipal;
import ru.itis.vhsroni.error.dto.OperationResponse;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.InternalException;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;
import ru.itis.vhsroni.profile.api.ProfileApi;
import ru.itis.vhsroni.profile.data.dto.request.UpdateBaseProfileInfoRequest;
import ru.itis.vhsroni.profile.data.dto.request.UpdateClientProfileInfoRequest;
import ru.itis.vhsroni.profile.data.dto.request.UpdateDentistProfileInfoRequest;
import ru.itis.vhsroni.profile.data.dto.request.UpdatePasswordRequest;
import ru.itis.vhsroni.profile.data.dto.response.BaseProfileResponse;
import ru.itis.vhsroni.profile.service.BaseProfileService;
import ru.itis.vhsroni.profile.service.impl.AdminProfileServiceImpl;
import ru.itis.vhsroni.profile.service.impl.ClientProfileServiceImpl;
import ru.itis.vhsroni.profile.service.impl.DentistProfileServiceImpl;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ProfileController implements ProfileApi {

    private final DentistProfileServiceImpl dentistProfileService;

    private final ClientProfileServiceImpl clientProfileService;

    private final AdminProfileServiceImpl adminProfileService;

    private final BaseProfileService baseProfileService;

    private final ErrorMessageProperties errorMessage;

    @Override
    public BaseProfileResponse getProfile(UnifiedAuthPrincipal authPrincipal) {
        Set<Role> roles = authPrincipal.getRoles();
        if (roles.contains(Role.DENTIST)) {
            return dentistProfileService.getProfile(authPrincipal.getUserId());
        } else if (roles.contains(Role.CLIENT)) {
            return clientProfileService.getProfile(authPrincipal.getUserId());
        } else if (roles.contains(Role.ADMIN) || roles.contains(Role.MASTER)){
            return adminProfileService.getProfile(authPrincipal.getUserId());
        }
        throw new InternalException(errorMessage.noRequiredRole(), ErrorCode.NO_REQUIRED_ROLE);
    }

    @Override
    public OperationResponse updateBaseProfileInformation(UnifiedAuthPrincipal authPrincipal, UpdateBaseProfileInfoRequest baseUpdateProfileRequest) {
        baseProfileService.updateBaseProfileInfo(authPrincipal.getUserId(), baseUpdateProfileRequest);
        return OperationResponse.builder().isSuccess(true).build();
    }

    @Override
    public OperationResponse updateDentistProfileInformation(UnifiedAuthPrincipal authPrincipal, UpdateDentistProfileInfoRequest dentistUpdateProfileRequest) {
        dentistProfileService.updateDentistProfileInfo(authPrincipal.getUserId(), dentistUpdateProfileRequest);
        return OperationResponse.builder().isSuccess(true).build();
    }

    @Override
    public OperationResponse updateClientProfileInformation(UnifiedAuthPrincipal authPrincipal, UpdateClientProfileInfoRequest clientUpdateProfileRequest) {
        clientProfileService.updateClientProfile(authPrincipal.getUserId(), clientUpdateProfileRequest);
        return OperationResponse.builder().isSuccess(true).build();
    }

    @Override
    public OperationResponse updatePassword(UnifiedAuthPrincipal authPrincipal, UpdatePasswordRequest updatePasswordRequest) {
        baseProfileService.updatePassword(authPrincipal.getUserId(), updatePasswordRequest);
        return OperationResponse.builder().isSuccess(true).build();
    }
}
