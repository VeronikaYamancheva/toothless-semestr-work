package ru.itis.vhsroni.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.vhsroni.admin.api.AdminConsoleApi;
import ru.itis.vhsroni.admin.data.dto.response.AdminConsoleDentistResponse;
import ru.itis.vhsroni.admin.data.dto.response.AdminConsoleUserResponse;
import ru.itis.vhsroni.admin.service.AdminManagementService;
import ru.itis.vhsroni.auth.data.enums.Role;
import ru.itis.vhsroni.auth.data.principal.UnifiedAuthPrincipal;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;
import ru.itis.vhsroni.error.dto.OperationResponse;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.InternalException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminConsoleController implements AdminConsoleApi {

    private final AdminManagementService adminManagementService;

    private final ErrorMessageProperties errorMessage;

    @Override
    public Page<AdminConsoleUserResponse> getAllUsersForAdmin(UnifiedAuthPrincipal authPrincipal, Pageable pageable) {
        Set<Role> roles = authPrincipal.getRoles();
        if (roles.contains(Role.MASTER)) {
            return adminManagementService.getAllUsersPageForMasterConsole(pageable);
        } else if (roles.contains(Role.ADMIN)) {
            return adminManagementService.getAllUsersPageForAdminConsole(pageable);
        } else {
            log.warn("User don't have MASTER/ADMIN role in Admin console");
            throw new InternalException(errorMessage.noRequiredRole(), ErrorCode.NO_REQUIRED_ROLE);
        }
    }

    @Override
    public OperationResponse updateAdminRole(UUID userId, boolean isAdmin) {
        adminManagementService.updateUserAdminRole(userId, isAdmin);
        return OperationResponse.builder().isSuccess(true).build();
    }

    @Override
    public OperationResponse updateBannedStatus(UUID userId, boolean isBanned) {
        adminManagementService.updateUserBannedState(userId, isBanned);
        return OperationResponse.builder().isSuccess(true).build();
    }

    @Override
    public List<AdminConsoleDentistResponse> getAllNotApprovedDentist() {
        return adminManagementService.getAllNotApprovedDentists();
    }

    @Override
    public OperationResponse approveDentist(UUID userId, boolean isApproved) {
        adminManagementService.approveDentist(isApproved, userId);
        return OperationResponse.builder().isSuccess(true).build();
    }
}
