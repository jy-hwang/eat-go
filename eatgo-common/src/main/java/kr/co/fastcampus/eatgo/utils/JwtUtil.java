package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {

    private Key key;

    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(Long userId, String nickname) {
        String token = Jwts.builder()
                .claim("userId", userId)
                .claim("nickname", nickname)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }
}
