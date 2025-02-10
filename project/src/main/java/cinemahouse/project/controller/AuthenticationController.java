package cinemahouse.project.controller;

import cinemahouse.project.dto.ApiResponse;
import cinemahouse.project.dto.request.AuthenticationRequest;
import cinemahouse.project.dto.request.IntrospectRequest;
import cinemahouse.project.dto.request.LogoutRequest;
import cinemahouse.project.dto.request.RefreshTokenRequest;
import cinemahouse.project.dto.response.AuthenticationResponse;
import cinemahouse.project.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import org.springframework.web.bind.annotation.*;
import java.text.ParseException;


public interface AuthenticationController {


     ApiResponse<AuthenticationResponse> outboundAuthenticateGoogle(@RequestParam("code") String code);



     ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);


     ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException;



     ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException;



     ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request)
            throws ParseException, JOSEException;


}
