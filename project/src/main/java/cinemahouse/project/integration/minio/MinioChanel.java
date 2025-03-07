//package cinemahouse.project.integration.minio;
//
//
//import cinemahouse.project.exception.AppException;
//import cinemahouse.project.exception.ErrorCode;
//import cinemahouse.project.utils.ConverterUtils;
//import io.minio.*;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.lang.NonNull;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Objects;
//import java.util.UUID;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class MinioChanel {
//    private static final String BUCKET = "resources";
//    private final MinioClient minioClient;
//
//    @PostConstruct
//    private void init() {
//        createBucket(BUCKET);//bucket nhu thu muc chua file anh
//    }
//
//    @SneakyThrows
//    private void createBucket(final String name) {
//        // Kiểm tra nếu bucket đã tồn tại
//        final var found = minioClient.bucketExists(
//                BucketExistsArgs.builder()
//                        .bucket(name)
//                        .build()
//        );
//        if (!found) {
//            // Tạo bucket nếu chưa tồn tại
//            minioClient.makeBucket(
//                    MakeBucketArgs.builder()
//                            .bucket(name)
//                            .build()
//            );
//
//            // Thiết lập bucket là public bằng cách set policy
//            final var policy = """
//                        {
//                          "Version": "2012-10-17",
//                          "Statement": [
//                           {
//                              "Effect": "Allow",
//                              "Principal": "*",
//                              "Action": "s3:GetObject",
//                              "Resource": "arn:aws:s3:::%s/*"
//                            }
//                          ]
//                        }
//                    """.formatted(name);
//            minioClient.setBucketPolicy(
//                    SetBucketPolicyArgs.builder().bucket(name).config(policy).build()
//            );
//        } else {
//            log.info("Bucket %s đã tồn tại.".formatted(name));
//        }
//    }
//
//    @SneakyThrows
//    public String upload(@NonNull final MultipartFile file) {
//        log.info("Bucket: {}, file size: {}", BUCKET, file.getSize());
//
//        // ✅ Tạo tên file duy nhất
//        String originalFileName = file.getOriginalFilename();
//        String fileExtension = Objects.requireNonNull(originalFileName)
//                .substring(originalFileName.lastIndexOf("."));
//        String uniqueFileName = System.currentTimeMillis() + "-" + UUID.randomUUID() + fileExtension;
//
//        try {
//            minioClient.putObject(
//                    PutObjectArgs.builder()
//                            .bucket(BUCKET)
//                            .object(uniqueFileName)
//                            .contentType(file.getContentType() == null ? "image/png" : file.getContentType())
//                            .stream(file.getInputStream(), file.getSize(), -1)
//                            .build()
//            );
//        } catch (Exception ex) {
//            log.error("Error saving image \n {} ", ex.getMessage());
//            throw new AppException(ErrorCode.UNABLE_UPLOAD_FILE);
//        }
//
//        // ✅ Trả về URL truy cập công khai
//        return minioClient.getPresignedObjectUrl(
//                GetPresignedObjectUrlArgs.builder()
//                        .method(io.minio.http.Method.GET)
//                        .bucket(BUCKET)
//                        .object(uniqueFileName)
//                        .build()
//        );
//    }
//
//    public byte[] download(String bucket, String name) {
//        try (GetObjectResponse inputStream = minioClient.getObject(GetObjectArgs.builder()
//                .bucket(bucket)
//                .object(name)
//                .build())) {
//            String contentLength = inputStream.headers().get(HttpHeaders.CONTENT_LENGTH);
//            int size = StringUtils.isEmpty(contentLength) ? 0 : Integer.parseInt(contentLength);
//            return ConverterUtils.readBytesFromInputStream(inputStream, size);
//        } catch (Exception e) {
//            throw new AppException(ErrorCode.UNABLE_DOWNLOAD_FILE);
//        }
//    }
//}
