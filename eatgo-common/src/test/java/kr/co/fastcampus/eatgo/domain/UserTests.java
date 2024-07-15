package kr.co.fastcampus.eatgo.domain;

import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(LoggingTestWatcher.class)
public class UserTests {

    @Test
    @DisplayName("유저 생성")
    public void creation() {
        User user
                = User.builder()
                .email("tester@example.com")
                .nickname("테스터11")
                .level(100L)
                .build();

        assertThat(user.getNickname(), is("테스터11"));
        assertThat(user.isAdmin(), is(true));
        assertThat(user.isActive(), is(true));

        user.deactivate();

        assertThat(user.isActive(), is(false));
    }

    @Test
    @DisplayName("비밀번호가 있는 액세스 토큰 관련 테스트")
    public void accessTokenWithPassword(){
        User user = User.builder().password("ACCESSTOKEN").build();

        assertThat(user.getPassword(), is("ACCESSTOKEN"));
    }
    @Test
    @DisplayName("비밀번호가 없는 액세스 토큰 관련 테스트")
    public void accessTokenWithoutPassword(){
        User user = new User();

        assertThat(user.getAccessToken(), is(""));
    }
}
