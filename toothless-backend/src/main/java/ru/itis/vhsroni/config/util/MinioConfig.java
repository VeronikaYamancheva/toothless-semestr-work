package ru.itis.vhsroni.config.util;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itis.vhsroni.config.property.MinioConfigProperties;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient(MinioConfigProperties minioConfigProperty) throws Exception{
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioConfigProperty.url())
                .credentials(minioConfigProperty.accessKey(), minioConfigProperty.secretKey())
                .build();

        String bucketName = minioConfigProperty.avatarsBucketName();
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        return minioClient;
    }
}
