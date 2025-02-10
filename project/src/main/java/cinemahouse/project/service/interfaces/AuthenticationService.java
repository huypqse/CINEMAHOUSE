package cinemahouse.project.service.interfaces;

import cinemahouse.project.dto.request.*;
import cinemahouse.project.dto.response.AuthenticationResponse;
import cinemahouse.project.dto.response.IntrospectResponse;
import cinemahouse.project.entity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse outboundAuthenticate(String code);

    String generateToken(User user);

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException;

    AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException;

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
