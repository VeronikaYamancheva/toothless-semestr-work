package ru.itis.vhsroni.auth.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.auth.data.entity.UserCredentials;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.data.enums.AuthProvider;
import ru.itis.vhsroni.auth.data.principal.CustomOAuth2User;
import ru.itis.vhsroni.auth.repository.UserCredentialsRepository;
import ru.itis.vhsroni.auth.repository.UserDataRepository;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.InternalException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final GithubOAuth2Service githubOAuth2Service;

    private final GoogleOAuth2Service googleOAuth2Service;

    private final UserDataRepository userDataRepository;

    private final UserCredentialsRepository userCredentialsRepository;

    private final ErrorMessageProperties errorMessage;

    @Override
    public CustomOAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        String accessToken = userRequest.getAccessToken().getTokenValue();
        String provider = userRequest.getClientRegistration().getRegistrationId();
        UUID userId = switch (provider) {
            case "github" -> githubOAuth2Service.processGithubOAuth2User(accessToken, attributes);
            case "google" -> googleOAuth2Service.processGoogleUser(accessToken, attributes);
            default -> throw new OAuth2AuthenticationException("Provider `%s` not found".formatted(provider));
        };
        UserData userData = userDataRepository.findById(userId)
                .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        List<UserCredentials> userCredentials = userCredentialsRepository.findByUserData(userData);
        Set<AuthProvider> authProviders = userCredentials.stream()
                .map(UserCredentials::getProviderType)
                .collect(Collectors.toSet());
        return new CustomOAuth2User(userData, attributes, authProviders);
    }
}
