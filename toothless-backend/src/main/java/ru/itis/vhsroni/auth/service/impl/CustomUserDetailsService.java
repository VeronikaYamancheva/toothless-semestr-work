package ru.itis.vhsroni.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.auth.data.entity.UserCredentials;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.data.enums.AuthProvider;
import ru.itis.vhsroni.auth.data.principal.CustomUserDetails;
import ru.itis.vhsroni.auth.repository.UserCredentialsRepository;
import ru.itis.vhsroni.auth.repository.UserDataRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDataRepository userDataRepository;

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData userData = userDataRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: %s not found".formatted(username)));
        List<UserCredentials> userCredentials = userCredentialsRepository.findByUserData(userData);
        Set<AuthProvider> authProviders = userCredentials.stream()
                .map(UserCredentials::getProviderType)
                .collect(Collectors.toSet());
        String hashedPassword = String.valueOf(userCredentials.stream().map(UserCredentials::getHashPassword).filter(Objects::nonNull).findFirst());
        return new CustomUserDetails(userData, authProviders, hashedPassword);
    }
}
