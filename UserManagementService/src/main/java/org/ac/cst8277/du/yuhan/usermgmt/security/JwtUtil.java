package org.ac.cst8277.du.yuhan.usermgmt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "cst8277-secret-key";
    private static final long EXPIRATION_MS = 60 * 60 * 1000; // 1 hour

    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

    // Generate JWT
    public static String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_MS);

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(ALGORITHM);
    }

    // test token
    public static void validateToken(String token) {
        JWT.require(ALGORITHM)
                .build()
                .verify(token);
    }

    //  Get username from token
    public static String getUsernameFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }
}
