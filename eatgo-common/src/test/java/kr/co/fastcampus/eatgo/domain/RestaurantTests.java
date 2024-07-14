package kr.co.fastcampus.eatgo.domain;

import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(LoggingTestWatcher.class)
public class RestaurantTests {

    @Test
    @DisplayName("Creation Test")
    public void creation() {
        Restaurant restaurant
                = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Busan")
                .build();

        assertThat(restaurant.getId(), Matchers.is(1004L));
        assertThat(restaurant.getName(), Matchers.is("Bob zip"));
        assertThat(restaurant.getAddress(), Matchers.is("Busan"));
    }

    @Test
    @DisplayName("Infomation Test")
    public void information() {
        Restaurant restaurant
                = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        assertThat(restaurant.getInformation(), Matchers.is("Bob zip in Seoul"));
    }

}
