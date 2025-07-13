package ru.itis.vhsroni.auth.data.principal;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.data.enums.AuthProvider;
import ru.itis.vhsroni.auth.data.enums.Role;
import ru.itis.vhsroni.auth.data.enums.State;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails, CredentialsContainer, UnifiedAuthPrincipal {

    private final UUID userId;

    private final String userEmail;

    private String hashedPassword;

    private final State accountState;

    private final Set<Role> userRoles;

    private final Set<AuthProvider> authProviders;

    public CustomUserDetails (UserData userData, Set<AuthProvider> authProviders, String hashedPassword) {
        this.userId = userData.getUserId();
        this.userEmail= userData.getEmail();
        this.hashedPassword = hashedPassword;
        this.accountState = userData.getState();
        this.userRoles = userData.getRoles();
        this.authProviders = authProviders;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    // account expiration is not expected
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountState == State.ACTIVE || accountState == State.NOT_VERIFIED;
    }

    // credentials expiration is not expected
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return accountState == State.ACTIVE;
    }

    @Override
    public void eraseCredentials() {
        this.hashedPassword = null;
    }

    @Override
    public UUID getUserId() {
        return userId;
    }

    @Override
    public String getEmail() {
        return userEmail;
    }

    @Override
    public State getAccountState() {
        return accountState;
    }

    @Override
    public Set<Role> getRoles() {
        return userRoles;
    }

    @Override
    public Set<AuthProvider> getAuthProviders() {
        return authProviders;
    }
}
