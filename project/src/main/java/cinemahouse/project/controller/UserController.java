package cinemahouse.project.controller;

import cinemahouse.project.dto.ApiResponse;
import cinemahouse.project.dto.request.*;
import cinemahouse.project.dto.response.UserResponse;
import cinemahouse.project.dto.response.VerifyOtpResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserController {

    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request,
                                                @RequestParam String otp);


    ApiResponse<Boolean> checkExistsUser(@RequestBody EmailRequest request);

    ApiResponse<Void> createPassword(@RequestBody @Valid PasswordCreationForFirstRequest request);


    ApiResponse<Void> sendOtpForgotPassword(@RequestBody EmailRequest request)
            throws MessagingException, UnsupportedEncodingException;


    ApiResponse<Void> sendOtpRegister(@RequestBody EmailRequest request)
            throws MessagingException, UnsupportedEncodingException;


    ApiResponse<VerifyOtpResponse> verifyOtp(@RequestBody VerifyOtpRequest request);


    ApiResponse<?> resetPassword(@RequestBody @Valid PasswordCreationRequest request );

    ApiResponse<List<UserResponse>> getUsers();

    ApiResponse<UserResponse> getMyInfo();
}
