package br.com.clinicaqr.infrastructure.security;

import br.com.clinicaqr.domain.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtService {
    private final SecretKey key;
    private final long expiration;
    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration-ms}") long expiration) {
        this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); this.expiration=expiration;
    }
    public String generate(User user) {
        Instant now=Instant.now();
        return Jwts.builder().subject(user.id().toString()).claim("email",user.email())
                .issuedAt(Date.from(now)).expiration(Date.from(now.plusMillis(expiration))).signWith(key).compact();
    }
    public UUID userId(String token) {
        return UUID.fromString(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject());
    }
}