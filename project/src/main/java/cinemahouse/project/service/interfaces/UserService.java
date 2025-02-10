package cinemahouse.project.service.interfaces;

import cinemahouse.project.dto.request.*;
import cinemahouse.project.dto.response.UserResponse;
import cinemahouse.project.dto.response.VerifyOtpResponse;
import cinemahouse.project.exception.AppException;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    boolean findByEmail(EmailRequest request);
    UserResponse createUser(UserCreationRequest request, String otp) throws AppException;
    void createPassword(PasswordCreationForFirstRequest request) throws AppException;
    List<UserResponse> getAllUsers();
    UserResponse getMyInfo();
    void sendOtpForgotPassword(EmailRequest request) throws MessagingException, UnsupportedEncodingException;
    void sendOtpRegister(EmailRequest request) throws MessagingException, UnsupportedEncodingException;
    VerifyOtpResponse verifyOtp(VerifyOtpRequest request);
    void resetPassword(PasswordCreationRequest request) throws AppException;
}
