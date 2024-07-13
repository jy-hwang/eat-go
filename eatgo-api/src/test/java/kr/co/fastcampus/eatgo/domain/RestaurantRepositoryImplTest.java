package kr.co.fastcampus.eatgo.domain;

import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(LoggingTestWatcher.class)
public class RestaurantRepositoryImplTest {

    @Test
    @DisplayName("가게 하나를 저장하는 테스트")
    public void save() {
        RestaurantRepository repository = new RestaurantRepositoryImpl();
        int oldCount = repository.findAll().size();

        Restaurant restaurant = new Restaurant("BeRyong", "Busan");
        repository.save(restaurant);
        assertThat(restaurant.getId(), is(1234L));

        int newCount = repository.findAll().size();

        assertThat(newCount - oldCount, is(1));
    }

}