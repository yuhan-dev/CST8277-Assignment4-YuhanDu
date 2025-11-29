package org.ac.cst8277.du.yuhan.usermgmt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {

    // TODO：如果你之前已经有固定的 secret，可以用你自己原来的字符串
    private static final String SECRET = "my-super-secret-key-for-assignment4";

    // token 有效期（毫秒）——例如 1 小时
    private static final long EXPIRATION_MS = 60 * 60 * 1000L;

    /**
     * 生成 JWT，sub = username
     */
    public static String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_MS);

        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .sign(algorithm);
    }

    /**
     * 校验 token 是否有效（签名正确 + 未过期）。
     * 无异常表示有效，有异常说明无效。
     */
    public static void validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();

        // 这行会在 token 失效/伪造时抛异常
        verifier.verify(token);
    }

    /**
     * 从 token 中提取 username（即 JWT 的 subject）
     */
    public static String extractUsername(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();   // 对应 withSubject(username)
    }
}
