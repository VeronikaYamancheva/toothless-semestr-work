package ru.itis.vhsroni.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itis.vhsroni.admin.data.dto.response.AdminConsoleDentistResponse;
import ru.itis.vhsroni.admin.data.dto.response.AdminConsoleUserResponse;

import java.util.List;
import java.util.UUID;

public interface AdminManagementService {

    Page<AdminConsoleUserResponse> getAllUsersPageForAdminConsole(Pageable pageable);

    Page<AdminConsoleUserResponse> getAllUsersPageForMasterConsole(Pageable pageable);

    void updateUserAdminRole(UUID userId, boolean isAdmin);

    void updateUserBannedState(UUID userId, boolean isBanned);

    List<AdminConsoleDentistResponse> getAllNotApprovedDentists();

    void approveDentist(boolean isApproved, UUID userId);
}
