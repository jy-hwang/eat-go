package kr.co.fastcampus.eatgo.domain;

import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(LoggingTestWatcher.class)
public class RestaurantTests {

    @Test
    @DisplayName("Creation Test")
    public void creation() {
        Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Busan");

        assertThat(restaurant.getId(), is(1004L));
        assertThat(restaurant.getName(), is("Bob zip"));
        assertThat(restaurant.getAddress(), is("Busan"));
    }

    @Test
    @DisplayName("Infomation Test")
    public void information() {
        Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Seoul");

        assertThat(restaurant.getInformation(), is("Bob zip in Seoul"));
    }

}
