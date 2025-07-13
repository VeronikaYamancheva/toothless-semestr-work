package ru.itis.vhsroni.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
public record MinioConfigProperties(
        String url,
        String accessKey,
        String secretKey,
        String avatarsBucketName
) {
}
