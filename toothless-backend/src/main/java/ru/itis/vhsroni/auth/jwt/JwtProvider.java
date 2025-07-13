package ru.itis.vhsroni.auth.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.itis.vhsroni.config.property.AuthConfigProperties;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.*;


@Slf4j
@Component
public class JwtProvider {
    private final AuthConfigProperties authConfig;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final JwtParser parser;

    public JwtProvider(AuthConfigProperties jwtConfigProperty) {
        this.authConfig = jwtConfigProperty;
        this.privateKey = loadPrivateKey(jwtConfigProperty.privateKey());
        this.publicKey = loadPublicKey(jwtConfigProperty.publicKey());
        this.parser = Jwts.parser().verifyWith(publicKey).build();
    }

    public String generateAccessToken(String subject, Map<String, Object> claims) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(authConfig.accessTokenExpiration().toSeconds());
        Map<String, Object> processedClaims = processClaimsForGeneration(claims);
        return Jwts.builder()
                .subject(subject)
                .claims(processedClaims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public String generateRefreshToken(String subject) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(authConfig.refreshTokenExpiration().toSeconds());
        return Jwts.builder()
                .subject(subject)
                .claim("jti", UUID.randomUUID().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public String extractSubject(String token) {
        Jws<Claims> jws = parser.parseSignedClaims(token);
        return jws.getPayload().getSubject();
    }

    public Claims extractAllClaims(String token) {
        Jws<Claims> jws = parser.parseSignedClaims(token);
        return jws.getPayload();
    }

    public boolean isTokenValid(String token) {
        try {
            parser.parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            log.warn("JwtException: {}", e.getMessage());
            return false;
        }
    }

    public UUID extractUuidClaim(String token, String claimName) {
        Claims claims = extractAllClaims(token);
        String uuidString = claims.get(claimName, String.class);
        return UUID.fromString(uuidString);
    }

    private PrivateKey loadPrivateKey(String privateKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load private key", e);
        }
    }

    private PublicKey loadPublicKey(String publicKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load public key", e);
        }
    }

    private Map<String, Object> processClaimsForGeneration(Map<String, Object> claims) {
        Map<String, Object> processedClaims = new HashMap<>(claims);
        processedClaims.replaceAll((key, value) -> {
            if (value instanceof UUID) {
                return value.toString();
            } else if (value instanceof Collection) {
                return new ArrayList<>((Collection<?>) value);
            }
            return value;
        });
        return processedClaims;
    }
}
