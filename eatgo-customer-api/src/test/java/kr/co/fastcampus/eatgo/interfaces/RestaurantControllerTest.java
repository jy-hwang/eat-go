package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(LoggingTestWatcher.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired // 테스트 관련 객체에 대해서는 Spring이 특별히 허용하고 있음.
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    @DisplayName("필터링 된 가게 목록을 가져오는 테스트")
    public void listWithParam() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();

        restaurants.add(
                Restaurant.builder()
                        .id(1002L)
                        .categoryId(1L)
                        .name("JOKER House")
                        .address("Seoul")
                        .build()
        );

        given(restaurantService.getRestaurants("Seoul",1L)).willReturn(restaurants);

        mvc.perform(get("/restaurants?region=Seoul&category=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1002")
                )).andExpect(content().string(
                        containsString("\"name\":\"JOKER House\"")
                ));
    }

    @Test
    @DisplayName("존재하는 특정 가게 하나를 가져오는 테스트")
    public void detailWithExisted() throws Exception {
        Restaurant restaurant1
                = Restaurant.builder()
                .id(1004L)
                .name("JOKER House")
                .address("Seoul")
                .build();

        MenuItem menuItem
                = MenuItem.builder()
                .name("Kimchi")
                .build();

        restaurant1.setMenuItems(List.of(menuItem));

        Review review
                = Review.builder()
                .name("jyhwang")
                .score(4)
                .description("gooood!")
                .build();

        restaurant1.setReviews(List.of(review));

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                )).andExpect(content().string(
                        containsString("\"name\":\"JOKER House\"")
                )).andExpect(content().string(
                        containsString("Kimchi")
                )).andExpect(content().string(
                        containsString("gooood")
                ));
    }

    @Test
    @DisplayName("존재하지 않는 특정 가게 하나를 가져오는 테스트")
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(404L)).willThrow(new RestaurantNotFoundException(404L));

        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

}
