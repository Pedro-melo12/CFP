package com.CFP.controleFinanceiroPessoal.service;

import com.CFP.controleFinanceiroPessoal.model.Users;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Users user) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create().withIssuer("my-application-product")
                    .withSubject(user.getEmail())
                    .withExpiresAt(getExpirationTime())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("my-application-product")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e);
        }
    }

    public Instant getExpirationTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
