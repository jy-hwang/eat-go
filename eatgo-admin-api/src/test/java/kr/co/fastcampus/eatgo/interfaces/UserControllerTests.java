package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(LoggingTestWatcher.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("사용자 전체 목록을 가져오기")
    public void list() throws Exception {
        List<User> users = new ArrayList<>();

        users.add(User.builder()
                .email("tester@example.com")
                .nickname("Tester")
                .level(1L)
                .build());

        given(userService.getUsers()).willReturn(users);

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Tester")));
        // TODO:  다른데서는 괜찮았는데 유독 여기서만 한글이 깨지는 문제가 있어서 적어놓고 감.
    }

    @Test
    @DisplayName("사용자를 생성하는 테스트")
    public void create() throws Exception {
        String email = "admin@example.com";
        String nickname = "Administrator";

        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .build();

        given(userService.addUser(email, nickname)).willReturn(user);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\" : \"admin@example.com\", \"nickname\":\"Administrator\"}")
        ).andExpect(status().isCreated());

        verify(userService).addUser(email, nickname);
    }

    @Test
    @DisplayName("사용자를 수정하는 테스트")
    public void update() throws Exception {
        mvc.perform(patch("/users/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"admin@example.com\"" +
                        ",\"nickname\" : \"Administrator\",\"level\":100}")
        ).andExpect(status().isOk());

        Long id = 1004L;
        String email = "admin@example.com";
        String nickname = "Administrator";
        Long level = 100L;

        verify(userService).updateUser(eq(id), eq(email), eq(nickname), eq(level));
    }

}