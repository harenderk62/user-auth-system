package com.harender.user_auth_system.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.rmi.server.ExportException;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

//    String secureKey = KeyGenerator.generateSecureKey();
//    System.out.println("Generated Secure Key: " + secureKey);

    private SecretKey getSecretKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(jwtSecret), SignatureAlgorithm.HS512.getJcaName());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, getSecretKey())
                .compact();
    }

    public String getUsernameFromJwt(String token) {

        JwtParser parser = Jwts.parser()
                .setSigningKey(getSecretKey())
//                .verifyWith(jwtSecret)
                .build();

        // Parse the JWT and extract the subject (username)
        return parser.parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateJwt(String token){
        try{
            Jwts.parser().setSigningKey(getSecretKey()).build().parseSignedClaims(token);
            return true;
        }catch (io.jsonwebtoken.security.SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex){
//        Log the exception
        }
        return false;
    }

}
