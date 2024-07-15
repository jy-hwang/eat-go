package kr.co.fastcampus.eatgo.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

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
        String secret = "";

        String token = jwtUtil.createToken(1004L, "John");

        assertThat(token, containsString("."));
    }

}
