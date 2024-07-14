package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(LoggingTestWatcher.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    @DisplayName("사용자 회원가입 서비스")
    public void registerUser() {
        String email = "tester@example.com";
        String nickname = "tester";
        String password = "test";

        userService.registerUser(email, nickname, password);

        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("존재하는 이메일인 경우 테스트")
    public void registerUserWithExistedEmail() {
        String email = "tester@example.com";
        String nickname = "tester";
        String password = "test";

        User user = User.builder().build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        EmailExistedException exception = assertThrows(EmailExistedException.class, () -> {
            userService.registerUser(email, nickname, password);
        });

        verify(userRepository, never()).save(any());
    }

}