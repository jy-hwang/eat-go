package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(LoggingTestWatcher.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired // 테스트 관련 객체에 대해서는 Spring이 특별히 허용하고 있음.
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    @DisplayName("가게 목록을 가져오는 테스트")
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();

        restaurants.add(
                Restaurant.builder()
                        .id(1002L)
                        .name("JOKER House")
                        .address("Seoul")
                        .build()
        );

        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1002")
                )).andExpect(content().string(
                        containsString("\"name\":\"JOKER House\"")
                ));
    }

    @Test
    @DisplayName("특정 가게 하나를 가져오는 테스트")
    public void detail() throws Exception {
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
        Restaurant restaurant2
                = Restaurant.builder()
                .id(2024L)
                .name("Cyber Food")
                .address("Universe")
                .build();

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);
        given(restaurantService.getRestaurant(2024L)).willReturn(restaurant2);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                )).andExpect(content().string(
                        containsString("\"name\":\"JOKER House\"")
                )).andExpect(content().string(
                        containsString("Kimchi")
                ));

        mvc.perform(get("/restaurants/2024"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":2024")
                )).andExpect(content().string(
                        containsString("\"name\":\"Cyber Food\"")
                ));
    }

    @Test
    @DisplayName("유효한 데이터로 가게 하나를 추가하는 테스트")
    public void createWithValidData() throws Exception {
        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                    .id(1234L)
                    .name(restaurant.getName())
                    .address(restaurant.getAddress())
                    .build();
        });

        mvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"BeRyong\",\"address\":\"Busan\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1234"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());
    }

    @Test
    @DisplayName("유효하지 않은 데이터가 입력되었을 때의 테스트")
    public void createWithInvalidData() throws Exception {
        mvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"address\":\"\"}")
                )
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("유효한 데이터로 가게 정보를 수정하는 테스트")
    public void updateWithValidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"JOKER Bar\",\"address\":\"Busan\"}"))
                .andExpect(status().isOk());
        verify(restaurantService).updateRestaurant(1004L, "JOKER Bar", "Busan");
    }
    @Test
    @DisplayName("유효하지 않은 데이터로 가게 정보를 수정하는 테스트")
    public void updateWithInvalidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());

    }
}
