package ru.itis.vhsroni.photo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.vhsroni.auth.data.principal.UnifiedAuthPrincipal;
import ru.itis.vhsroni.error.dto.OperationResponse;
import ru.itis.vhsroni.photo.service.AvatarService;
import ru.itis.vhsroni.photo.api.PhotoApi;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PhotoController implements PhotoApi {

    private final AvatarService avatarService;

    @Override
    public ResponseEntity<byte[]> getAvatar(UUID userId) {
        byte[] content = avatarService.getAvatar(userId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(content);
    }

    @Override
    public OperationResponse uploadAvatar(UnifiedAuthPrincipal authPrincipal, MultipartFile file) {
        avatarService.uploadAvatar(authPrincipal.getUserId(), file);
        return OperationResponse.builder().isSuccess(true).build();
    }
}
