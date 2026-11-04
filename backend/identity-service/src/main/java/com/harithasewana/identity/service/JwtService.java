
package com.harithasewana.identity.service;

import com.harithasewana.identity.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;


    //SecretKey -> Represents the secret key used by HS256
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);   //Converts the Base64 secret into bytes
        return Keys.hmacShaKeyFor(keyBytes);  //Creates a cryptographic signing key
    }

    public String generateToken(User user) {

        return Jwts.builder()  //Creates a JWT builder object. We use this object to construct our token
                .subject(user.getId().toString())  //sub - Sets the subject of the token ------------------------------
                .claim("email", user.getEmail())  //email - Adds a custom claim to the token                    |->PAYLOAD BODY
                .issuedAt(new Date())  //iat (Issued At)                                                              |
                .expiration(new Date(System.currentTimeMillis() + expiration))  //exp (expiration time)----------------
                .signWith(getSigningKey())//--------------------------------------------------------------------------->SIGNATURE
                .compact();  //Builds the final JWT string
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()  //Creates a JWT parser
                .verifyWith(getSigningKey())  //Supplies the key used to verify the signature
                .build()
                .parseSignedClaims(token) //Parses and verifies the signed JWT
                .getPayload();
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    public Long extractUserId(String token) {
        return Long.parseLong(
                extractAllClaims(token).getSubject()
        );
    }

}
