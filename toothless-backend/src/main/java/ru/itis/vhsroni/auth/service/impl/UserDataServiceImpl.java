package ru.itis.vhsroni.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.auth.data.dto.form.BaseRegistrationForm;
import ru.itis.vhsroni.auth.data.dto.inner.CreateOAuthUserData;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.data.enums.State;
import ru.itis.vhsroni.auth.data.mapper.UserDataMapper;
import ru.itis.vhsroni.auth.repository.UserDataRepository;
import ru.itis.vhsroni.auth.service.UserDataService;
import ru.itis.vhsroni.config.property.AuthConfigProperties;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.BadRequestException;
import ru.itis.vhsroni.error.exception.ForbiddenException;
import ru.itis.vhsroni.error.exception.InternalException;
import ru.itis.vhsroni.profile.data.dto.inner.BaseProfileInfo;
import ru.itis.vhsroni.profile.data.dto.request.UpdateBaseProfileInfoRequest;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDataServiceImpl implements UserDataService {

    private final UserDataRepository userDataRepository;

    private final UserDataMapper userDataMapper;

    private final AuthConfigProperties authConfig;

    private final ErrorMessageProperties errorMessage;

    @Override
    public UUID createUserData(BaseRegistrationForm baseRegistrationForm, boolean isDentist) {
        UserData userData = userDataRepository.save(userDataMapper.toUserData(baseRegistrationForm, isDentist));
        return userData.getUserId();
    }

    @Override
    public void verifyUserEmail(String email) {
        UserData userData = userDataRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        if (userData.getState() != State.NOT_VERIFIED) {
            throw new ForbiddenException(errorMessage.emailAlreadyVerified(), ErrorCode.EMAIL_ALREADY_VERIFIED);
        }
        userData.setState(State.ACTIVE);
        userDataRepository.save(userData);
    }

    public boolean checkIsEmailNotVerified(String email) {
        UserData userData = userDataRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        return userData.getState() == State.NOT_VERIFIED;
    }

    @Override
    public boolean checkIsEmailAvailable(String email) {
        return !userDataRepository.existsByEmail(email);
    }

    @Override
    public Map<String, Object> getJwtClaimsMapByEmail(String email) {
        UserData userData = userDataRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        String fullName = String.join(" ",
                userData.getFirstName(),
                Optional.ofNullable(userData.getMiddleName()).orElse(""),
                userData.getLastName()
        ).replaceAll("\\s+", " ").trim();
        return Map.of(
                authConfig.jwtUserIdKey(), userData.getUserId(),
                authConfig.jwtRolesKey(), userData.getRoles(),
                authConfig.jwtNameKey(), fullName
        );
    }

    @Override
    public UUID createOAuthUserData(CreateOAuthUserData createOAuthUserData) {
        UserData userData = userDataRepository.save(userDataMapper.toUserData(createOAuthUserData));
        return userData.getUserId();
    }

    @Override
    public BaseProfileInfo getBaseProfileInfoByUserId(UUID userId) {
        UserData userData = userDataRepository.findUserDataByUserId(userId)
                .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        return userDataMapper.toBaseProfileInf(userData);
    }

    @Override
    public void updateBaseProfileInfo(UUID userId, UpdateBaseProfileInfoRequest updateBaseProfileInfoRequest) {
        UserData userData = userDataRepository.findUserDataByUserId(userId)
                .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        userData.setFirstName(updateBaseProfileInfoRequest.firstName());
        userData.setLastName(updateBaseProfileInfoRequest.lastName());
        userData.setMiddleName(updateBaseProfileInfoRequest.middleName());
        userData.setBirthDate(updateBaseProfileInfoRequest.birthDate());
        userData.setPhoneNumber(updateBaseProfileInfoRequest.phoneNumber());
        userDataRepository.save(userData);
    }

    @Override
    public String getAvatarUrlByUserId(UUID userId) {
        UserData userData = userDataRepository.findUserDataByUserId(userId)
                .orElseThrow(() -> new BadRequestException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        return userData.getAvatarUrl();
    }

    @Override
    public void updateAvatarUrl(UUID userId, String url) {
        UserData userData = userDataRepository.findUserDataByUserId(userId)
                .orElseThrow(() -> new BadRequestException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        userData.setAvatarUrl(url);
        userDataRepository.save(userData);
    }

    @Override
    public void uploadAvatar(UUID userId, String fileName) {
        UserData userData = userDataRepository.findUserDataByUserId(userId)
                .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        userData.setAvatarUrl(fileName);
        userDataRepository.save(userData);

    }
}
