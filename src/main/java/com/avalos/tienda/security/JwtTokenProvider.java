package com.avalos.tienda.security;

import com.avalos.tienda.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Usuario user){
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("rol", user.getRol().name())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }
    public String getEmailFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
    }
    public String getRolFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().get("rol", String.class);
    }
}
