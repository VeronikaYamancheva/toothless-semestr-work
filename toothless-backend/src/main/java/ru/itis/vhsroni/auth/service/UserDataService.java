package ru.itis.vhsroni.auth.service;

import ru.itis.vhsroni.auth.data.dto.form.BaseRegistrationForm;
import ru.itis.vhsroni.auth.data.dto.inner.CreateOAuthUserData;
import ru.itis.vhsroni.profile.data.dto.inner.BaseProfileInfo;
import ru.itis.vhsroni.profile.data.dto.request.UpdateBaseProfileInfoRequest;

import java.util.Map;
import java.util.UUID;

public interface UserDataService {

    UUID createUserData(BaseRegistrationForm registrationForm, boolean isDentist);

    UUID createOAuthUserData(CreateOAuthUserData createOAuthUserData);

    void verifyUserEmail(String email);

    boolean checkIsEmailNotVerified(String email);

    boolean checkIsEmailAvailable(String email);

    Map<String, Object> getJwtClaimsMapByEmail(String email);

    BaseProfileInfo getBaseProfileInfoByUserId(UUID userId);

    void updateBaseProfileInfo(UUID userId, UpdateBaseProfileInfoRequest updateBaseProfileInfoRequest);

    String getAvatarUrlByUserId(UUID userId);

    void updateAvatarUrl(UUID userId, String url);

    void uploadAvatar(UUID userId, String fileName);
}
