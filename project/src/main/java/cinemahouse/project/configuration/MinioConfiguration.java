//package cinemahouse.project.configuration;
//
//import cinemahouse.project.configuration.minio.MinioProperties;
//import io.minio.MinioClient;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.BeanCreationException;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RequiredArgsConstructor
//public class MinioConfiguration {
//
//    private static final Logger logger = LoggerFactory.getLogger(MinioConfiguration.class);
//
//    private final MinioProperties minioProperties;
//
//    @Bean
//    public MinioClient minioClient() {
//        validateMinioProperties();
//
//        try {
//            MinioClient minioClient = MinioClient.builder()
//                    .endpoint(minioProperties.getUrl())
//                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
//                    .build();
//
//            logger.info("Successfully created MinioClient for endpoint: {}", minioProperties.getUrl());
//            return minioClient;
//        } catch (Exception e) {
//            logger.error("Error creating MinioClient for endpoint: {}", minioProperties.getUrl(), e);
//            throw new BeanCreationException("Error creating MinioClient bean", e);
//        }
//    }
//
//    private void validateMinioProperties() {
//        if (minioProperties.getUrl() == null || minioProperties.getUrl().isBlank()) {
//            throw new IllegalArgumentException("Minio URL must not be null or empty");
//        }
//        if (minioProperties.getAccessKey() == null || minioProperties.getAccessKey().isBlank()) {
//            throw new IllegalArgumentException("Minio Access Key must not be null or empty");
//        }
//        if (minioProperties.getSecretKey() == null || minioProperties.getSecretKey().isBlank()) {
//            throw new IllegalArgumentException("Minio Secret Key must not be null or empty");
//        }
//    }
//}
