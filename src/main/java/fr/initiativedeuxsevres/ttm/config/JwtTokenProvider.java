package fr.initiativedeuxsevres.ttm.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String jwtSecret;
    @Value("${security.jwt.token.expiration-milliseconds}")
    private long jwtExpiration;
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        String role = authentication.getAuthorities().toString();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpiration);
        return Jwts.builder()
                .subject(username) // définit le sujet du token
                .claim("role", role)
                .issuedAt(new Date()) // date d'émission du token
                .expiration(expireDate) // date d'expiration
                .signWith(key()) // signe le token avec la secret key
                .compact(); // compacte le token en une chaine
    }
    public SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public String getUsername(String token) {
        return Jwts.parser() // crée un parser
                .verifyWith(key())
                .build() // construit le parser
                .parseSignedClaims(token)// parse les claims
                .getPayload() // recup le payload du token
                .getSubject(); // recup le username
    }
    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
