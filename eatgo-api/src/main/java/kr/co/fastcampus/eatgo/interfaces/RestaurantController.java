package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.Restarurant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {


    @GetMapping
    public List<Restarurant> list(){
        List<Restarurant> restaurants = new ArrayList<>();

        Restarurant restarurant  = new Restarurant(1004L, "Bob zip","Seoul");
        restaurants.add(restarurant);

        return restaurants;
    }

}
