package org.example.agrikarmabackend.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    /*
     * Secret key used for signing JWT.
     * Must be at least 256 bits for HS256.
     * In production, this should come from environment variables.
     */
    private static final String SECRET =
            "agrikarma_secure_jwt_secret_key_which_is_long_enough";

    private final SecretKey secretKey;


     // Token validity duration (15 mins)   - short token access
     private static final long EXPIRATION_TIME = 15 * 60 * 1000;

    public JwtUtil() {
        this.secretKey = Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8)
        );
    }


     // Generates a JWT token containing user email and role.
    public String generateToken(String email, String role) {

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }


    public boolean isTokenValid(String token) {
        return getClaims(token).getExpiration().after(new Date());
    }

    // this parses JWT and returns claims.
    private Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
