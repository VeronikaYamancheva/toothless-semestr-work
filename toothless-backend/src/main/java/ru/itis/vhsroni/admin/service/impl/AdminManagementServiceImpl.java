package ru.itis.vhsroni.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.admin.data.dto.response.AdminConsoleDentistResponse;
import ru.itis.vhsroni.admin.data.dto.response.AdminConsoleUserResponse;
import ru.itis.vhsroni.admin.service.AdminManagementService;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.data.enums.Role;
import ru.itis.vhsroni.auth.data.enums.State;
import ru.itis.vhsroni.auth.data.mapper.UserDataMapper;
import ru.itis.vhsroni.auth.repository.UserDataRepository;
import ru.itis.vhsroni.client.service.ClientService;
import ru.itis.vhsroni.dentist.service.DentistService;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.BadRequestException;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminManagementServiceImpl implements AdminManagementService {

    private final UserDataRepository userDataRepository;

    private final UserDataMapper userDataMapper;

    private final ClientService clientService;

    private final DentistService dentistService;

    private final ErrorMessageProperties errorMessage;

    @Override
    public Page<AdminConsoleUserResponse> getAllUsersPageForAdminConsole(Pageable pageable) {
        return getAllUsersForAdminConsole(false, pageable);
    }

    @Override
    public Page<AdminConsoleUserResponse> getAllUsersPageForMasterConsole(Pageable pageable) {
        return getAllUsersForAdminConsole(true, pageable);
    }

    private Page<AdminConsoleUserResponse> getAllUsersForAdminConsole(boolean isMaster, Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("lastName").ascending()
                        .and(Sort.by("firstName").ascending())
                        .and(Sort.by("middleName").ascending())
        );
        log.debug("Sorted pageable: {}", pageable);
        Page<UserData> users = userDataRepository.findAllActiveUsersWithoutMasterRole(sortedPageable);
        return users.map(userData -> userDataMapper.toAdminConsoleResponse(userData, isMaster));
    }

    @Override
    public void updateUserAdminRole(UUID userId, boolean isAdmin) {
        UserData userData = userDataRepository.findUserDataByUserId(userId)
                .orElseThrow(() -> new BadRequestException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        Set<Role> roles = userData.getRoles();
        if (isAdmin) {
            roles.add(Role.ADMIN);
            log.debug("Role ADMIN add to user with id {}", userData.getUserId());
        } else {
            roles.remove(Role.ADMIN);
            log.debug("Role ADMIN remove from user with id {}", userData.getUserId());
        }
        userData.setRoles(roles);
        userDataRepository.save(userData);
    }

    @Override
    public void updateUserBannedState(UUID userId, boolean isBanned) {
        UserData userData = userDataRepository.findUserDataByUserId(userId)
                .orElseThrow(() -> new BadRequestException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        if (isBanned) {
            userData.setState(State.BANNED);
            log.debug("State BANNED set to user with id {}", userData.getUserId());
        } else {
            userData.setState(State.ACTIVE);
            log.debug("State ACTIVE set to user with id {}", userData.getUserId());
        }
        userDataRepository.save(userData);
    }

    @Override
    public List<AdminConsoleDentistResponse> getAllNotApprovedDentists() {
        List<UserData> userData = userDataRepository.findAllNotApprovedActiveUsers();
        return userData.stream().map(userDataMapper::toAdminConsoleDentistResponse).collect(Collectors.toList());
    }

    @Override
    public void approveDentist(boolean isApproved, UUID userId) {
        UserData approvedUser = userDataRepository.findUserDataByUserId(userId)
                .orElseThrow(() -> new BadRequestException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        approvedUser.setAdminApproval(true);
        Set<Role> roles = approvedUser.getRoles();
        if (isApproved) {
            roles.remove(Role.CLIENT);
            roles.add(Role.DENTIST);
            log.debug("user with id: {} was approved as dentist, his roles: {}", approvedUser.getUserId(), roles);
        }
        approvedUser.setRoles(roles);
        userDataRepository.save(approvedUser);
        clientService.deleteClientByUserId(userId);
        dentistService.createNewDentistByUserId(userId);
    }
}
