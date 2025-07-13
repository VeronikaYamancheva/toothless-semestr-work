package ru.itis.vhsroni.auth.data.principal;

import ru.itis.vhsroni.auth.data.enums.AuthProvider;
import ru.itis.vhsroni.auth.data.enums.Role;
import ru.itis.vhsroni.auth.data.enums.State;

import java.util.Set;
import java.util.UUID;

public interface UnifiedAuthPrincipal {

    UUID getUserId();

    String getEmail();

    State getAccountState();

    Set<Role> getRoles();

    Set<AuthProvider> getAuthProviders();
}
