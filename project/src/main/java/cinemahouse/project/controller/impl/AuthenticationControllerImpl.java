package cinemahouse.project.controller.impl;

import cinemahouse.project.controller.AuthenticationController;
import cinemahouse.project.dto.ApiResponse;
import cinemahouse.project.dto.request.AuthenticationRequest;
import cinemahouse.project.dto.request.IntrospectRequest;
import cinemahouse.project.dto.request.LogoutRequest;
import cinemahouse.project.dto.request.RefreshTokenRequest;
import cinemahouse.project.dto.response.AuthenticationResponse;
import cinemahouse.project.dto.response.IntrospectResponse;
import cinemahouse.project.service.AuthenticationServiceImpl;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationControllerImpl implements AuthenticationController {

    AuthenticationServiceImpl authenticationService;

    @PostMapping("/outbound/authentication")
    @Override
    public ApiResponse<AuthenticationResponse> outboundAuthenticateGoogle(@RequestParam("code") String code) {
        var result = authenticationService.outboundAuthenticate(code);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/token")
    @Override
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);

        return ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    @Override
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {

        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    @Override
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {

        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Logout Successfully")
                .build();
    }

    @PostMapping("/refresh")
    @Override
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }

}
