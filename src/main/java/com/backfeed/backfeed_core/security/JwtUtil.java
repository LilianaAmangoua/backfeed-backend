package com.backfeed.backfeed_core.security;

import com.backfeed.backfeed_core.dtos.InvitationToken;
import com.backfeed.backfeed_core.exceptions.JwtTokenExpiredException;
import com.backfeed.backfeed_core.exceptions.JwtValidationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;
    @Value("${invitation.expiration}")
    private int invitationExpiration;
    private SecretKey key;
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public JwtToken generateToken(Integer userId){
        return new JwtToken(
                Jwts.builder()
                        .setSubject(userId.toString())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact()
        );
    }

    public InvitationToken generateInvitationToken(String invitationId){
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + invitationExpiration);

        String token = Jwts.builder()
                    .setSubject(invitationId)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + invitationExpiration))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        return new InvitationToken(token, expirationDate);
    }

    public String getDataFromToken(String token) {
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
            log.warn("Invalid JWT signature: {}", e.getMessage());
            throw new JwtValidationException("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.warn("Malformed JWT token: {}", e.getMessage());
            throw new JwtValidationException("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token: {}", e.getMessage());
            throw new JwtTokenExpiredException("JWT token has expired.");
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT token: {}", e.getMessage());
            throw new JwtValidationException("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
            throw new JwtValidationException("JWT claims string is empty.");
        }

    }


}
