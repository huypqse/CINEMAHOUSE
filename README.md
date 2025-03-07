## Cinemahouse Service – Online Ticket Booking Platform  

## Overview  
**Cinemahouse Service** is an online movie ticket booking platform that allows users to **select seats in real-time** and make **secure payments via VNPay**. The system is built with **Spring Boot, PostgreSQL, WebSocket, Redis,  ensuring **high performance, real-time booking updates.  

## Features  
- **Real-time seat selection and booking** using WebSocket.  
- **Secure payment integration** with VNPay.  
- **Conflict resolution mechanism** to handle simultaneous seat bookings.  
- **Optimized performance** with Redis caching.  

## Prerequisites
Before starting, make sure you have installed the following tools:
- **JDK 21+** ([Download JDK](https://adoptopenjdk.net/))
- **Maven 3.5+** ([Download Maven](https://maven.apache.org/download.cgi))
- **IntelliJ IDEA** ([Download IntelliJ](https://www.jetbrains.com/idea/))

## Technology Stack
This project utilizes the following technologies:
- **Java 21**
- **Spring Boot 3.2.5**
- **PostgreSQL**
- **Redis**
- **Maven 3.5+**
- **Lombok**
- **DevTools**
- **Docker & Docker Compose**

## Setup & Run Application

### 1. Build the Application
```sh
mvn clean package -P dev|test|uat|prod
```

### 2. Run the Application
#### Run using Maven Wrapper
```sh
./mvnw spring-boot:run
```
#### Run using JAR
```sh
java -jar target/cinemahouse-app.jar
```

### 3. Run using Docker
#### Build Docker Image
```sh
docker build -t cinemahouse-app .
```
#### Run Docker Container
```sh
docker run -d --name cinemahouse-app cinemahouse-app:latest
```

## Environment Configuration
The environment configuration can be set using environment variables or the `application.yml` configuration file.

## Contact
If you have any questions or issues, please contact us huypqse@gmail.com or create an issue on GitHub.

