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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(LoggingTestWatcher.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        userService = new UserService(passwordEncoder, userRepository);
    }

    @Test
    @DisplayName("유효한 정보로 인증처리 테스트")
    public void authenticateWithValidAttributes() {
        String email = "tester@example.com";
        String password = "test";
        User mockUser = User.builder().email(email).build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.authenticate(email, password);

        assertThat(user.getEmail()).isEqualTo(email);
    }


    @Test
    @DisplayName("존재하지 않는 이메일로 인증처리 테스트")
    public void authenticateWithNotExistedEmail() {
        String email = "x@example.com";
        String password = "test";
        User mockUser = User.builder().email(email).build();

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        EmailNotExistedException exception = assertThrows(EmailNotExistedException.class, () -> {
            userService.authenticate(email, password);
        });

    }

    @Test
    @DisplayName("잘못된 비밀번호로 인증처리 테스트")
    public void authenticateWithWrongPassword() {
        String email = "tester@example.com";
        String password = "x";

        User mockUser = User.builder().email(email).build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        PasswordWrongException exception = assertThrows(PasswordWrongException.class, () -> {
            userService.authenticate(email, password);
        });
    }
}
