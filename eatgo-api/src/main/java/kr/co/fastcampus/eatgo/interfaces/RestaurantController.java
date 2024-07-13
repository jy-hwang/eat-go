package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
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
    public List<Restaurant> list() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        return restaurants;
    }

    @GetMapping("/{id}")
    public Restaurant detail(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.getRestaurant(id); // 기본 정보 + 메뉴정보

        return restaurant;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Restaurant resource) throws URISyntaxException {
        Restaurant restaurant
                = Restaurant.builder()
                .name(resource.getName())
                .address(resource.getAddress())
                .build();

        Restaurant saved = restaurantService.addRestaurant(restaurant);
        URI uri = new URI("/restaurants/" + saved.getId());
        return ResponseEntity.created(uri).body("{}");
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @RequestBody Restaurant resource) {
        String name = resource.getName();
        String address = resource.getAddress();
        restaurantService.updateRestaurant(id, name, address);
        return "{}";
    }
}
