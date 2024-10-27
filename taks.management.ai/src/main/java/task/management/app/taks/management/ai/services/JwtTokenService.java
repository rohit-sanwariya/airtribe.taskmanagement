package task.management.app.taks.management.ai.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import task.management.app.taks.management.ai.dtos.TokenGenerateResponse;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtTokenService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;
    
    public TokenGenerateResponse generateToken(String username){
        HashMap<String,Object> claims = new HashMap<>();
        return createToken(claims,username);
    }
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private TokenGenerateResponse createToken(HashMap<String, Object> claims, String username) {
        TokenGenerateResponse tokenGenerateResponse = new TokenGenerateResponse();
        LocalDateTime issuedAt = LocalDateTime.now();
        Instant instant = issuedAt.atZone(ZoneId.systemDefault()).toInstant();
        LocalDateTime expiresIn = LocalDateTime.now().plusSeconds(expirationTime);
        Instant instant2 = expiresIn.atZone(ZoneId.systemDefault()).toInstant();
        String token =  Jwts
        .builder()
        .claims(claims)
        .subject(username)
        .issuedAt(Date.from(instant))
        .expiration(Date.from(instant2))
        .signWith(getSigningKey())
        .compact();
        tokenGenerateResponse.setToken(token);
        tokenGenerateResponse.setIssued_at(issuedAt);
        tokenGenerateResponse.setExpires_at(expiresIn);
        return tokenGenerateResponse;
    }
    public boolean isTokenValid(String jwt, UserDetails user) {
        final String username = extractUsername(jwt);
        return username.equals(user.getUsername()) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        final Date expiration = extractClaims(jwt).getExpiration();
        return expiration.before(new Date());
    }

    public String extractUsername(String jwt) {
        final Claims claims = extractClaims(jwt);
        return claims.getSubject();
    }


    private Claims extractClaims(String jwt) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseClaimsJws(jwt).getPayload();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }
}



