package br.com.fiap.smartdrones.service;

import br.com.fiap.smartdrones.model.Token;
import br.com.fiap.smartdrones.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    private UserDetailsService authService;

    public String generateToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant expiresAt = LocalDateTime.now().plusMinutes(120).toInstant(ZoneOffset.ofHours(-3));

        return JWT.create()
                .withIssuer("smartdrones-api")
                .withSubject(userDetails.getUsername())
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }

    public UserDetails getUserFromToken(String jwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            var jwtVerifier = JWT.require(algorithm).withIssuer("smartdrones-api").build();
            var jwtVerified = jwtVerifier.verify(jwt);
            String username = jwtVerified.getSubject();
            return authService.loadUserByUsername(username);
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado.", exception);
        }
    }
}