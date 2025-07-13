package ru.itis.vhsroni.photo.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AvatarService {

    String uploadAvatarFromUrl(String url, String accessToken);

    void uploadAvatar(UUID userId, MultipartFile file);

    byte[] getAvatar(UUID userId);
}
