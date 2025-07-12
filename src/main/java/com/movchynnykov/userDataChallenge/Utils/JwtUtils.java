package com.movchynnykov.userDataChallenge.Utils;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	@Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;
    private SecretKey key;
    
    @PostConstruct
    public void initialize() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
    
    public String generateToken(String email) {
    	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentTimestamp)
                .setExpiration(new Date(currentTimestamp.getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String parseTokenForEmail(String token) {
    	return Jwts.parserBuilder()
    		   .setSigningKey(key).build()
    		   .parseClaimsJws(token)
    		   .getBody()
    		   .getSubject();
    }
}
