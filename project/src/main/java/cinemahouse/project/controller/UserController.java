package cinemahouse.project.controller;

import cinemahouse.project.dto.ApiResponse;
import cinemahouse.project.dto.request.*;
import cinemahouse.project.dto.response.UserResponse;
import cinemahouse.project.dto.response.VerifyOtpResponse;
import cinemahouse.project.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request,
                                                @RequestParam String otp) {
        var result = userService.createUser(request, otp);

        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(result)
                .build();
    }

    @PostMapping("/check-exists-user")
    ApiResponse<Boolean> checkExistsUser(@RequestBody EmailRequest request){
        var result = userService.findByEmail(request);
        System.out.println(result);

        return ApiResponse.<Boolean>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }

    @PostMapping("/create-password")
    ApiResponse<Void> createPassword(@RequestBody @Valid PasswordCreationForFirstRequest request){
        userService.createPassword(request);

        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Password has ben created, you could use it to log-in")
                .build();
    }

    @PostMapping("/send-otp")
    ApiResponse<Void> sendOtpForgotPassword(@RequestBody EmailRequest request)
            throws MessagingException, UnsupportedEncodingException {

        userService.sendOtpForgotPassword(request);

        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Send Otp Successfully")
                .build();
    }

    @PostMapping("/send-otp-register")
    ApiResponse<Void> sendOtpRegister(@RequestBody EmailRequest request)
            throws MessagingException, UnsupportedEncodingException {
        userService.sendOtpRegister(request);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Send Otp Successfully")
                .build();
    }

    @PostMapping("/verify-otp")
    public ApiResponse<VerifyOtpResponse> verifyOtp(@RequestBody VerifyOtpRequest request) {
        var result = userService.verifyOtp(request);

        return ApiResponse.<VerifyOtpResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Verify Otp Successfully")
                .result(result)
                .build();
    }

    @PostMapping("/reset-password")
    ApiResponse<?> resetPassword(@RequestBody @Valid PasswordCreationRequest request ){
        userService.resetPassword(request);

        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Reset Password Successfully")
                .build();
    }

    @GetMapping("/users")
    ApiResponse<List<UserResponse>> getUsers() {
        var result = userService.getAllUsers();

        return ApiResponse.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo(){
        var result = userService.getMyInfo();
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();

    }
}
