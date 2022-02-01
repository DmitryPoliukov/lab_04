package com.epam.esm.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.epam.esm.repository.dto.UserCredentialDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@PropertySource(value = {"classpath:application.properties"})

public class JwtHandler {

    @Value("${jwt.expiration}")
    private long expirationInMinutes;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(String email) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(Date.from(LocalDateTime.now().plusMinutes(expirationInMinutes)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .sign(algorithm);
    }
}
