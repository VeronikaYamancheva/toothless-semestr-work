package ru.itis.vhsroni.auth.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.itis.vhsroni.auth.data.dto.inner.CreateOAuthUserData;
import ru.itis.vhsroni.auth.data.enums.AuthProvider;
import ru.itis.vhsroni.auth.service.UserCredentialsService;
import ru.itis.vhsroni.auth.service.UserDataService;
import ru.itis.vhsroni.client.service.ClientService;
import ru.itis.vhsroni.photo.service.AvatarService;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleOAuth2Service{

    private final RestClient restClient;

    private final UserCredentialsService userCredentialsService;

    private final UserDataService userDataService;

    private final AvatarService avatarService;

    private final ClientService clientService;

    public static final String GOOGLE_PERSON_BIRTHDAY_INFO_ENDPOINT =
            "https://people.googleapis.com/v1/people/me?personFields=birthdays";

    public static final String GOOGLE_PERSON_PHONE_INFO_ENDPOINT =
            "https://people.googleapis.com/v1/people/me?personFields=phoneNumbers";

    public static final String GOOGLE_BASIC_USER_INFO_ENDPOINT = "https://www.googleapis.com/oauth2/v3/userinfo";

    public UUID processGoogleUser (String accessToken, Map<String, Object> attributes) {
        GoogleApiBasicUserInfoResponse basicInfo = fetchGoogleUserBasicInfo(accessToken);
        String email = basicInfo.email;
        log.debug("Get user google email - {}", email);
        UUID userId = userCredentialsService.credentialsExist(email, AuthProvider.GOOGLE);
        if (userId == null) {
            String firstName = basicInfo.givenName;
            String lastName = basicInfo.familyName;
            String phone = fetchGoogleUserPhoneNumber(accessToken);
            LocalDate birthDate = fetchGoogleUserBirthDate(accessToken);
            String googleAvatarUrl = basicInfo.picture;
            String savedAvatarUrl = avatarService.uploadAvatarFromUrl(googleAvatarUrl, accessToken);
            CreateOAuthUserData createOAuthUserData = CreateOAuthUserData.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .birthDate(birthDate)
                    .email(email)
                    .phoneNumber(phone)
                    .avatarUrl(savedAvatarUrl)
                    .build();
            log.debug("Get user github data - firstName: {}, lastName: {}, birthdate: {}, email: {}, phoneNumber: {}, savedAvatarUrl: {}",
                    firstName, lastName, birthDate, email, phone, savedAvatarUrl);
            userId = userDataService.createOAuthUserData(createOAuthUserData);
            clientService.createClientByUserId(userId);
            userCredentialsService.createOAuthUserCredentials(userId, email, AuthProvider.GOOGLE);
        }
        return userId;
    }

    private GoogleApiBasicUserInfoResponse fetchGoogleUserBasicInfo(String accessToken) {
        return restClient.get()
                .uri(GOOGLE_BASIC_USER_INFO_ENDPOINT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .body(GoogleApiBasicUserInfoResponse.class);
    }

    private LocalDate fetchGoogleUserBirthDate(String accessToken) {
        BirthDayGoogleResponse birthdaysResponse = restClient.get()
                .uri(GOOGLE_PERSON_BIRTHDAY_INFO_ENDPOINT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .body(BirthDayGoogleResponse.class);

        if (birthdaysResponse.birthdays == null) {
            return null;
        }

        return Optional.of(birthdaysResponse)
                .map(BirthDayGoogleResponse::birthdays)
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0).date())
                .map(date -> LocalDate.of(date.year(), date.month(), date.day()))
                .orElse(null);
    }

    private String fetchGoogleUserPhoneNumber(String accessToken) {
        PhoneNumberGoogleResponse phoneNumbersResponse = restClient.get()
                .uri(GOOGLE_PERSON_PHONE_INFO_ENDPOINT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .body(PhoneNumberGoogleResponse.class);

        if (phoneNumbersResponse.phoneNumbers == null) {
            return null;
        }

        return phoneNumbersResponse.phoneNumbers.stream()
                .filter(phone -> phone.metadata.primary)
                .findFirst()
                .map(phone -> phone.value)
                .orElse(null);
    }

    private record BirthDayGoogleResponse(

            List<BirthdayGoogleDto> birthdays
    ) {}

    private record BirthdayGoogleDto(

            @JsonProperty("date") Date date,
            @JsonProperty("metadata") Metadata metadata
    ) {
        public record Date(
                @JsonProperty("year") int year,
                @JsonProperty("month") int month,
                @JsonProperty("day") int day
        ) {}

        public record Metadata(
                @JsonProperty("primary") boolean primary
        ) {}

    }

    private record PhoneNumberGoogleResponse(

            List<PhoneNumberGoogleDto> phoneNumbers
    ) {}

    private record PhoneNumberGoogleDto(

            @JsonProperty("value") String value,
            @JsonProperty("type") String type,
            @JsonProperty("metadata") Metadata metadata
    ) {
        public record Metadata(
                @JsonProperty("primary") boolean primary
        ) {}

    }

    private record GoogleApiBasicUserInfoResponse(
            @JsonProperty("given_name") String givenName,
            @JsonProperty("family_name") String familyName,
            @JsonProperty("email") String email,
            @JsonProperty("picture") String picture
    ) {}
}


