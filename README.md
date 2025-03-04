# Cinemahouse Service

## Prerequisites
Trước khi bắt đầu, hãy đảm bảo bạn đã cài đặt các công cụ sau:
- **JDK 21+** ([Download JDK](https://adoptopenjdk.net/))
- **Maven 3.5+** ([Download Maven](https://maven.apache.org/download.cgi))
- **IntelliJ IDEA** ([Download IntelliJ](https://www.jetbrains.com/idea/))

## Technology Stack
Dự án sử dụng các công nghệ sau:
- **Java 21**
- **Spring Boot 3.2.5**
- **PostgreSQL**
- **Redis**
- **Maven 3.5+**
- **Lombok**
- **DevTools**
- **Docker & Docker Compose**

## Cài đặt & Chạy ứng dụng

### 1. Build ứng dụng
```sh
mvn clean package -P dev|test|uat|prod
```

### 2. Chạy ứng dụng
#### Chạy bằng Maven Wrapper
```sh
./mvnw spring-boot:run
```
#### Chạy bằng JAR
```sh
java -jar target/backend-service.jar
```

### 3. Chạy bằng Docker
#### Build Docker Image
```sh
docker build -t backend-service .
```
#### Run Docker Container
```sh
docker run -d --name backend-service backend-service:latest
```

## Cấu hình môi trường
Cấu hình môi trường có thể được thiết lập thông qua biến môi trường hoặc file cấu hình `application.yml`.

## Liên hệ
Nếu có bất kỳ câu hỏi hoặc vấn đề nào, vui lòng liên hệ với chúng tôi qua email hoặc tạo issue trên GitHub.

