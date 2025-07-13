package ru.itis.vhsroni.photo.service.impl;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.vhsroni.auth.service.UserDataService;
import ru.itis.vhsroni.config.property.MinioConfigProperties;
import ru.itis.vhsroni.error.exception.InternalException;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.NotFoundException;
import ru.itis.vhsroni.error.exception.BadRequestException;
import ru.itis.vhsroni.photo.service.AvatarService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioAvatarServiceImpl implements AvatarService {

    private final MinioClient minioClient;

    private final RestClient restClient;

    private final MinioConfigProperties minioConfig;

    private final UserDataService userDataService;

    private final ErrorMessageProperties errorMessage;

    @Override
    public String uploadAvatarFromUrl(String url, String accessToken) {
        log.info("Attempting to upload avatar from URL: {}", url);

        String filename = minioConfig.avatarsBucketName() + "/" + UUID.randomUUID() + "-avatar.jpg";

        try {
            byte[] imageBytes = restClient.get()
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .onStatus(
                            status -> status == HttpStatus.NOT_FOUND,
                            (request, response) -> {
                                log.error("Avatar URL not found: {}", url);
                                throw new NotFoundException(errorMessage.avatarUrlNotFound(), ErrorCode.AVATAR_URL_NOT_FOUND);
                            })
                    .onStatus(
                            HttpStatusCode::is4xxClientError,
                            (request, response) -> {
                                log.error("Bad request when fetching avatar from URL: {}", url);
                                throw new BadRequestException(errorMessage.invalidAvatarUrl(), ErrorCode.INVALID_AVATAR_URL);
                            })
                    .body(byte[].class);

            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes)) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(minioConfig.avatarsBucketName())
                                .object(filename)
                                .stream(inputStream, imageBytes.length, -1)
                                .contentType("image/jpeg")
                                .build()
                );
                log.info("Successfully uploaded avatar from URL: {}", url);
                return filename;
            }
        } catch (HttpClientErrorException e) {
            log.error("HTTP client error when fetching avatar from URL: {}", url, e);
            throw new BadRequestException(errorMessage.invalidAvatarUrl(), ErrorCode.INVALID_AVATAR_URL);
        } catch (Exception e) {
            log.error("Failed to upload avatar from URL: {}", url, e);
            throw new ru.itis.vhsroni.error.exception.InternalException(errorMessage.failedUploadAvatar(), ErrorCode.FAILED_UPLOAD_AVATAR);
        }
    }

    @Override
    public void uploadAvatar(UUID userId, MultipartFile file) {
        log.info("Attempting to upload avatar for user: {}", userId);

        if (file == null || file.isEmpty()) {
            log.error("Empty file provided for user: {}", userId);
            throw new BadRequestException(errorMessage.emptyFile(), ErrorCode.EMPTY_FILE);
        }

        String filename = minioConfig.avatarsBucketName() + "/" + UUID.randomUUID() + "-avatar.jpg";

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioConfig.avatarsBucketName())
                            .object(filename)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            log.info("Successfully uploaded avatar for user: {}", userId);
            userDataService.uploadAvatar(userId, filename);
        } catch (IOException e) {
            log.error("IO error when uploading avatar for user: {}", userId, e);
            throw new BadRequestException(errorMessage.invalidFile(), ErrorCode.INVALID_FILE);
        } catch (Exception e) {
            log.error("Failed to upload avatar for user: {}", userId, e);
            throw new InternalException(errorMessage.failedUploadAvatar(), ErrorCode.FAILED_UPLOAD_AVATAR);
        }
    }

    @Override
    public byte[] getAvatar(UUID userId) {
        log.info("Attempting to get avatar for user: {}", userId);

        String url = userDataService.getAvatarUrlByUserId(userId);
        if (url == null) {
            log.error("Avatar not found for user: {}", userId);
            throw new NotFoundException(errorMessage.avatarNotFound(), ErrorCode.AVATAR_NOT_FOUND);
        }

        try {
            GetObjectResponse object = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioConfig.avatarsBucketName())
                            .object(url)
                            .build()
            );
            byte[] content = object.readAllBytes();
            log.info("Successfully retrieved avatar for user: {}", userId);
            return content;
        } catch (ErrorResponseException e) {
            log.error("Avatar file not found in storage for user: {}", userId, e);
            throw new NotFoundException(errorMessage.avatarNotFoundInStorage(), ErrorCode.AVATAR_NOT_FOUND_IN_STORAGE);
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException |
                 InsufficientDataException | InternalException |
                 InvalidResponseException | ServerException | XmlParserException e) {
            log.error("Failed to retrieve avatar for user: {}", userId, e);
            throw new BadRequestException(errorMessage.failedGetAvatar(), ErrorCode.FAILED_GET_AVATAR);
        } catch (Exception e) {
            log.error("Unexpected error when retrieving avatar for user: {}", userId, e);
            throw new InternalException(errorMessage.failedGetAvatar(), ErrorCode.FAILED_UPLOAD_AVATAR);
        }
    }
}