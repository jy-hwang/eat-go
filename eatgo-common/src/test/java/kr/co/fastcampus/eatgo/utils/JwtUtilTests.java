package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
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
        String token = jwtUtil.createToken(1004L, "John", null);

        assertThat(token).contains(".");
    }

    @Test
    @DisplayName("claims 를 가져오는 테스트")
    // @Disabled
    public void getClaims() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsIm5pY2tuYW1lIjoidGVzdGVyMTEifQ.O59d2_9fj_I78G3rrLCJdb3Caqx5zh-WJIv7OInIM_0";

        Claims claims = jwtUtil.getClaims(token);

        assertThat(claims.get("userId", Long.class)).isEqualTo(1L);
        assertThat(claims.get("nickname")).isEqualTo("tester11");

    }
}