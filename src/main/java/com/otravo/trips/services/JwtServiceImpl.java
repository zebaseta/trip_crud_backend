package com.otravo.trips.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.otravo.trips.exceptions.BusinessLogicException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String privateKeyId;

    @Override
    public String createToken(String id, String name, String email) throws BusinessLogicException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKeyId);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            String token =
                    JWT.create()
                            .withIssuedAt(new Date())
                            .withClaim("user_id", id)
                            .withClaim("user_email", email)
                            .withClaim("user_name", name)
                            .withExpiresAt(calendar.getTime())
                            .withIssuer("auth0")
                            .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new BusinessLogicException("Cannot create token");
        }
    }

    @Override
    public String verifyTokenAndGetUser(String token) throws BusinessLogicException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKeyId);
            JWTVerifier verifier =
                    JWT.require(algorithm).withIssuer("auth0").build(); // Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("user_name").asString();
        } catch (JWTVerificationException exception) {
            throw new BusinessLogicException("Token is not valid");
        }
    }

}
