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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(LoggingTestWatcher.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("사용자 목록 가져오기")
    public void getUsers() {
        List<User> mockUsers = new ArrayList<>();

        mockUsers.add(User.builder()
                .email("tester@example.com")
                .nickname("테스터")
                .level(1L)
                .build());

        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users = userService.getUsers();

        User user = users.get(0);

        assertThat(user.getNickname(), is("테스터"));
    }

    @Test
    @DisplayName("사용자를 생성하는 테스트")
    public void addUser() {
        String email = "admin@example.com";
        String nickname = "Administrator";

        User mockUser = User.builder()
                .email(email)
                .nickname(nickname)
                .build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.addUser(email, nickname);

        assertThat(user.getNickname(), is(nickname));
    }

    @Test
    @DisplayName("사용자를 수정하는 테스트")
    public void updateUser() {
        Long id = 1004L;
        String email = "admin@example.com";
        String nickname = "SuperPower";
        Long level = 100L;

        User mockUser = User.builder()
                .id(id)
                .email(email)
                .nickname("Administrator")
                .build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));
        User user = userService.updateUser(id, email, nickname, level);

        verify(userRepository).findById(eq(id));

        assertThat(user.getNickname(), is(nickname));
        assertThat(user.isAdmin(), is(true));
    }
}
