package ru.itis.vhsroni.auth.data.principal;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.data.enums.AuthProvider;
import ru.itis.vhsroni.auth.data.enums.Role;
import ru.itis.vhsroni.auth.data.enums.State;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomOAuth2User implements OAuth2User, UnifiedAuthPrincipal {

    private final UUID userId;

    private final String userEmail;

    private final Set<Role> userRoles;

    private final State accountState;

    private final Set<AuthProvider> authProviders;

    private final Map<String, Object> attributes;

    public CustomOAuth2User(UserData userData, Map<String, Object> attributes, Set<AuthProvider> authProviders) {
        this.userId = userData.getUserId();
        this.userEmail = userData.getEmail();
        this.userRoles = userData.getRoles();
        this.attributes = attributes;
        this.authProviders = authProviders;
        this.accountState = userData.getState();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return userEmail;
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
