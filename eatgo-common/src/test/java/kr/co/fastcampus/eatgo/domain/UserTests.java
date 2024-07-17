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
    @DisplayName("가게의 소유주인지를 검사하는 테스트")
    public void restaurantOwner() {
        User user = User.builder().level(1L).build();

        assertThat(user.isRestaurantOwner(), is(false));

        user.setRestaurantId(1004L);

        assertThat(user.isRestaurantOwner(), is(true));

        assertThat(user.getRestaurantId(), is(1004L));
    }
}
