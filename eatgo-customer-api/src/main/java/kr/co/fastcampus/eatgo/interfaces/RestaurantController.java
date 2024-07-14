package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired // 필드 주입보다 생성자 주입의 사용을 권장함.
    public RestaurantController(
            RestaurantService restaurantService
    ) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> list(
            @RequestParam("region") String region,
            @RequestParam("category") Long categoryId

    ) {
        List<Restaurant> restaurants = restaurantService.getRestaurants(region, categoryId);

        return restaurants;
    }

    @GetMapping("/{id}")
    public Restaurant detail(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.getRestaurant(id); // 기본 정보 + 메뉴정보

        return restaurant;
    }

}
