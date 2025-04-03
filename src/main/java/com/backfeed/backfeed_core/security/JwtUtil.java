package com.backfeed.backfeed_core.security;

import com.backfeed.backfeed_core.exceptions.JwtTokenExpiredException;
import com.backfeed.backfeed_core.exceptions.JwtValidationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public JwtToken generateToken(String email, String name, Optional<String> teamCode){
        return new JwtToken(
                Jwts.builder()
                        .setSubject(email)
                        .claim("name", name)
                        .claim("teamCode", teamCode)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact()
        );
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            throw new JwtValidationException("Signature JWT invalide");
        } catch (MalformedJwtException e) {
            throw new JwtValidationException("Token JWT invalid");
        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException("Token JWT est expiré");
        } catch (UnsupportedJwtException e) {
            throw new JwtValidationException("Token JWT n'est pas pris en charge");
        } catch (IllegalArgumentException e) {
            throw new JwtValidationException("Claims JWT vides");
        }
    }


}
