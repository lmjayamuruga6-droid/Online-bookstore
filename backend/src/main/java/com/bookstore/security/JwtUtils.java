package com.bookstore.security;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${app.jwtSecret}") private String jwtSecret;
    @Value("${app.jwtExpirationMs}") private int jwtExpirationMs;

    public String generateToken(String username) {
        return Jwts.builder().setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validate(String token) {
        try { Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token); return true; }
        catch(Exception e) { return false; }
    }
}