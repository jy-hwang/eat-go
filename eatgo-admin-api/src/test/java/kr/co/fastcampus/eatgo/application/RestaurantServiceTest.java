package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(LoggingTestWatcher.class)
public class RestaurantServiceTest {

    private RestaurantService restaurantService;
    @Mock
    private RestaurantRepository restaurantRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this); //TODO:이건 나중에 변경해보자.
        mockRestaurantRepository();
        restaurantService = new RestaurantService(restaurantRepository);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant1
                = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        restaurants.add(restaurant1);

        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant1));
    }

    @Test
    @DisplayName("가게 목록을 가져오는 테스트")
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        Restaurant restaurant = restaurants.get(0);

        assertThat(restaurant.getId(), is(1004L));
    }

    @Test
    @DisplayName("존재하는 특정 가게 하나를 가져오는 테스트")
    public void getRestaurantWithExisted() {
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        assertThat(restaurant.getId(), is(1004L));
    }

    @Test
    @DisplayName("존재하지 않는 특정 가게 하나를 가져오는 테스트")
    public void getRestaurantWithNotExisted() {
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantService.getRestaurant(404L);
        });
    }

    @Test
    @DisplayName("가게 하나를 추가하는 테스트")
    public void addRestaurant() {
        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });

        Restaurant restaurant
                = Restaurant.builder()
                .name("BeRyong")
                .address("Busan")
                .build();

        Restaurant created = restaurantService.addRestaurant(restaurant);

        assertThat(created.getId(), is(1234L));
    }

    @Test
    @DisplayName("가게 정보를 수정하는 테스트")
    public void updateRestaurant() {
        Restaurant restaurant
                = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Busan")
                .build();

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L, "Sool zip", "Busan");

        assertThat(restaurant.getName(), is("Sool zip"));
        assertThat(restaurant.getAddress(), is("Busan"));
    }
}