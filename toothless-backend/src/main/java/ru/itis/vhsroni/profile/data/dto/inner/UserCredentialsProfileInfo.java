package ru.itis.vhsroni.profile.data.dto.inner;

import lombok.Builder;
import ru.itis.vhsroni.auth.data.enums.AuthProvider;

import java.util.List;

@Builder
public record UserCredentialsProfileInfo(
       List<AuthProviderResponse> authProviders

) {
    @Builder
    public record AuthProviderResponse(
            AuthProvider authProvider,
            String providerKey
    ){}
}
