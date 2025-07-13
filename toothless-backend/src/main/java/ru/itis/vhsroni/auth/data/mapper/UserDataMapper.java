package ru.itis.vhsroni.auth.data.mapper;

import org.springframework.stereotype.Component;
import ru.itis.vhsroni.admin.data.dto.response.AdminConsoleDentistResponse;
import ru.itis.vhsroni.admin.data.dto.response.AdminConsoleUserResponse;
import ru.itis.vhsroni.auth.data.dto.form.BaseRegistrationForm;
import ru.itis.vhsroni.auth.data.dto.inner.CreateOAuthUserData;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.data.enums.Role;
import ru.itis.vhsroni.auth.data.enums.State;
import ru.itis.vhsroni.profile.data.dto.inner.BaseProfileInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class UserDataMapper {

    public UserData toUserData(BaseRegistrationForm registrationForm, boolean isDentist) {
        return UserData.builder()
                .firstName(registrationForm.getFirstName())
                .lastName(registrationForm.getLastName())
                .middleName(registrationForm.getMiddleName())
                .birthDate(registrationForm.getBirthDate())
                .email(registrationForm.getEmail())
                .phoneNumber(registrationForm.getPhoneNumber())
                .state(State.NOT_VERIFIED)
                .roles(Set.of(Role.CLIENT))
                .adminApproval(!isDentist)
                .build();
    }

    public UserData toUserData(CreateOAuthUserData createOAuthUserData){
        return UserData.builder()
                .firstName(createOAuthUserData.firstName())
                .lastName(createOAuthUserData.lastName())
                .birthDate(createOAuthUserData.birthDate())
                .email(createOAuthUserData.email())
                .phoneNumber(createOAuthUserData.phoneNumber())
                .state(State.ACTIVE)
                .roles(Set.of(Role.CLIENT))
                .adminApproval(true)
                .avatarUrl(createOAuthUserData.avatarUrl())
                .build();
    }

    public AdminConsoleUserResponse toAdminConsoleResponse(UserData userData, boolean isMaster) {
        boolean isAdmin = false;
        List<Role> newRoles = new ArrayList<>();
        Set<Role> roles = userData.getRoles();
        for (Role role : roles) {
            if (role == Role.ADMIN) {
                isAdmin = true;
            } else {
                newRoles.add(role);
            }
        }
        Boolean isBanned = null;
        if (isMaster) {
            isBanned = (userData.getState() == State.BANNED);
        }
        return AdminConsoleUserResponse.builder()
                .userId(userData.getUserId())
                .firstName(userData.getFirstName())
                .lastName(userData.getLastName())
                .middleName(userData.getMiddleName())
                .roles(newRoles)
                .isAdmin(isAdmin)
                .isBanned(isBanned)
                .build();
    }

    public AdminConsoleDentistResponse toAdminConsoleDentistResponse(UserData userData) {
        return AdminConsoleDentistResponse.builder()
                .userId(userData.getUserId())
                .firstName(userData.getFirstName())
                .lastName(userData.getLastName())
                .middleName(userData.getMiddleName())
                .build();
    }

    public BaseProfileInfo toBaseProfileInf(UserData userData){
        return BaseProfileInfo.builder()
                .firstName(userData.getFirstName())
                .lastName(userData.getLastName())
                .middleName(userData.getMiddleName())
                .birthDate(userData.getBirthDate())
                .email(userData.getEmail())
                .phoneNumber(userData.getPhoneNumber())
                .adminApproval(userData.isAdminApproval())
                .build();
    }
}
