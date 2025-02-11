package org.unibl.etf.ip.rentalservice.util;

/*import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expirationMs}")
    private long expirationMs;

    public String generateToken(UserDetails userDetails) {
        Timestamp expirationTime = new Timestamp(System.currentTimeMillis() + expirationMs);

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Timestamp(System.currentTimeMillis()))
                .withExpiresAt(expirationTime)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String extractUsername(String token) {
        return decodeToken(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return decodeToken(token).getExpiresAt().before(new Timestamp(System.currentTimeMillis()));
    }

    private DecodedJWT decodeToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
        return verifier.verify(token);
    }
}*/

