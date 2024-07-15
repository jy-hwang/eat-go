package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.EmailNotExistedException;
import kr.co.fastcampus.eatgo.application.PasswordWrongException;
import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(LoggingTestWatcher.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("세션 테스트 요청")
    @WithMockUser
    public void createWithValidAttributes() throws Exception {
        mvc.perform(post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"tester@example.com\",\"password\":\"test\"}")
                        .with(csrf()))  // CSRF 토큰 추가
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/session"))
                .andExpect(content().string("{\"accessToken\":\"ACCESSTOKEN\"}"));

        verify(userService).authenticate(eq("tester@example.com"), eq("test"));
    }

    @Test
    @DisplayName("세션 실패 테스트 요청 - 존재하지 않는 이메일")
    @WithMockUser
    public void createWithNotExistedEmail() throws Exception {
        given(userService.authenticate("x@example.com", "test")).willThrow(EmailNotExistedException.class);

        mvc.perform(post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"x@example.com\",\"password\":\"test\"}")
                        .with(csrf()))  // CSRF 토큰 추가
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("x@example.com"), eq("test"));
    }

    @Test
    @DisplayName("세션 실패 테스트 요청 - 잘못된 비밀번호")
    @WithMockUser
    public void createWithWrongPassword() throws Exception {
        given(userService.authenticate("tester@example.com", "x")).willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"tester@example.com\",\"password\":\"x\"}")
                        .with(csrf()))  // CSRF 토큰 추가
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("tester@example.com"), eq("x"));
    }

}
