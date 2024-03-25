package com.demo.pjglmicrousers.services;

import com.demo.pjglmicrousers.requests.UsuarioDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration.ms}")
    private long expirationTimeMs;

    public String generateToken(UsuarioDto usuario) {

        // Create claims containing user information
        byte[] secretKeyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);
        SecretKey key = Keys.hmacShaKeyFor(secretKeyBytes);

        return Jwts.builder()
                .setIssuer("pjgl-micro-users")
                .setSubject(usuario.getUsername())
                .claim("email", usuario.getEmail())
                .setIssuedAt(Date.from(Instant.now())) // Use Instant.now() for issuedAt
                .setExpiration(Date.from(Instant.now().plusMillis(expirationTimeMs))) // Use Instant.now().plusMillis for expiration
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}

