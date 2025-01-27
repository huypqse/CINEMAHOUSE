package cinemahouse.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    EMAIL_EXISTED(1008, "Email existed, please choose another one", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1009, "Username existed, please choose another one", HttpStatus.BAD_REQUEST),
    USERNAME_IS_MISSING(1010, "Please enter username", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1011, "User not existed", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(1012, "Role not existed", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN(401, "EXPIRED_TOKEN", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(401, "You need to log in to perform this action.", HttpStatus.UNAUTHORIZED),
    INVALID_OTP(400, "OTP is invalid or expired", HttpStatus.BAD_REQUEST),
    PASSWORD_EXISTED(409, "Password existed", HttpStatus.CONFLICT),
    UNABLE_UPLOAD_FILE(400, "Unable to upload file", HttpStatus.BAD_REQUEST),
    UNABLE_DOWNLOAD_FILE(400, "Unable to download file", HttpStatus.BAD_REQUEST),
    CINEMA_NOT_EXISTED(400, "Cinema not existed", HttpStatus.BAD_REQUEST),
    ROOMTYPE_VIP__NOT_EXISTED(400, "Roomtype vip not existed", HttpStatus.BAD_REQUEST),
    SCREENING_ROOM_NOT_EXISTED(400, "Screening room not existed", HttpStatus.BAD_REQUEST),
    MOVIE_NOT_EXISTED(400, "Movie not existed", HttpStatus.BAD_REQUEST),
    SEAT_TYPE_NOT_EXISTED(400, "Seat type not existed", HttpStatus.BAD_REQUEST),
    PRICE_NOT_EXISTED(400, "This price not existed", HttpStatus.BAD_REQUEST),


    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final HttpStatusCode statusCode;
    private final String message;
}
