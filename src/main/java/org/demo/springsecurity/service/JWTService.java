package org.demo.springsecurity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTService {

    private final String secretKey = "kL3zxIB+uLKvPbsHt8NuuwIHbW0FnM/JwhTmaEEVjQk=";

//    private final String secretKey;
//
//    public JWTService() {
//        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
//            javax.crypto.SecretKey secret = keyGenerator.generateKey();
//            secretKey = Base64.getEncoder().encodeToString(secret.getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }

    public String generateToken(String username) {

//        String base64Key = Encoders.BASE64.encode(Jwts.SIG.HS256.key().build().getEncoded());
//        System.out.println(base64Key);
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && isTokenValid(token));
    }

    private boolean isTokenValid(String token) {
        return extractExpiration(token).after(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
