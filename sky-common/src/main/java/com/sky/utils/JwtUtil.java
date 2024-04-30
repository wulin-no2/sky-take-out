package com.sky.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * generate jwt
     * use Hs256 algorithm, use constant key
     *
     * @param secretKey jwt key
     * @param ttlMillis jwt expire time(ms)
     * @param claims    set info
     * @return token
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // algorithm, part of header
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // time generate JWT
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // set jwt body
        JwtBuilder builder = Jwts.builder()
                // if there is private claim, must set this private claim for builder to get value.
                // once write after standard claim, will cover standard claim.
                .setClaims(claims)
                // set algorithm and key
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                // set expire time
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * Token parse
     *
     * @param secretKey jwt key must be saved in server, can't be exposed, or the sign can be faked, if facing multiple clients, set several keys
     * @param token     encrypted token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token) {
        // get DefaultJwtParser
        Claims claims = Jwts.parser()
                // set key for the sign
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                // set jwt to be parsed
                .parseClaimsJws(token).getBody();
        return claims;
    }

}
