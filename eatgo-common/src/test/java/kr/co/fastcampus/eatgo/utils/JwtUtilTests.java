package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtUtilTests {

    private static final String SECRET = "12345678123456781234567812345678";

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    @DisplayName("토큰을 생성하는 테스트")
    public void createToken() {
        String token = jwtUtil.createToken(1004L, "John");

        assertThat(token).contains(".");
    }

    @Test
    @DisplayName("claims 를 가져오는 테스트")
    @Disabled
    public void getClaims() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5pY2tuYW1lIjoiSm9obiJ9.eTVRsy7AkkWHQ-BLbrebFUiOetWAXw1aqT7ezsFm0y";

        Claims claims = jwtUtil.getClaims(token);

        assertThat(claims.get("userId", Long.class)).isEqualTo(1004L);
        assertThat(claims.get("name")).isEqualTo("John");

    }
}