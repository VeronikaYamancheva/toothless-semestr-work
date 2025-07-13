package ru.itis.vhsroni.auth.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.itis.vhsroni.auth.data.dto.inner.CreateOAuthUserData;
import ru.itis.vhsroni.auth.data.enums.AuthProvider;
import ru.itis.vhsroni.auth.service.UserCredentialsService;
import ru.itis.vhsroni.auth.service.UserDataService;
import ru.itis.vhsroni.client.service.ClientService;
import ru.itis.vhsroni.photo.service.AvatarService;

import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class GithubOAuth2Service {

    private final RestClient restClient;

    private final UserCredentialsService userCredentialsService;

    private final UserDataService userDataService;

    private final ClientService clientService;

    private final AvatarService avatarService;

    private static final String GITHUB_EMAILS_ENDPOINT = "https://api.github.com/user/emails";

    public UUID processGithubOAuth2User(String accessToken, Map<String, Object> attributes) {
        String email = fetchGithubPrimaryEmail(accessToken);
        log.debug("Get user github email - {}", email);
        UUID userId = userCredentialsService.credentialsExist(email, AuthProvider.GITHUB);
        if (userId == null) {
            String name = (String) attributes.get("name");
            String githubAvatarUrl = (String) attributes.get("avatar_url");
            String savedAvatarUrl = avatarService.uploadAvatarFromUrl(githubAvatarUrl, accessToken);
            CreateOAuthUserData createOAuthUserData = CreateOAuthUserData.builder()
                    .firstName(name)
                    .email(email)
                    .avatarUrl(savedAvatarUrl)
                    .build();
            log.debug("Get user github data - name: {}, email: {}, savedAvatarUrl: {}", name, email, savedAvatarUrl);
            userId = userDataService.createOAuthUserData(createOAuthUserData);
            clientService.createClientByUserId(userId);
            userCredentialsService.createOAuthUserCredentials(userId, email, AuthProvider.GITHUB);
        }
        return userId;
    }


    private String fetchGithubPrimaryEmail(String accessToken) {
        GithubEmailDto[] emails = restClient
                .get()
                .uri(GITHUB_EMAILS_ENDPOINT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .retrieve()
                .body(GithubEmailDto[].class);

        return Arrays.stream(emails)
                .filter(GithubEmailDto::primary)
                .findFirst()
                .map(GithubEmailDto::email)
                .orElseThrow(() -> new OAuth2AuthenticationException("Primary GitHub email not found"));
    }

    private record GithubEmailDto(
            String email,
            boolean primary,
            boolean verified,
            String visibility
    ) {
    }
}
