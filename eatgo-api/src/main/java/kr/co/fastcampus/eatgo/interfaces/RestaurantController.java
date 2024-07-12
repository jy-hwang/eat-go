package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.Restarurant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public Restarurant detail(@PathVariable("id") Long id){

        List<Restarurant> restaurants = new ArrayList<>();

        restaurants.add(new Restarurant(1004L, "Bob zip","Seoul"));
        restaurants.add(new Restarurant(2024L, "Cyber Food","Seoul"));

        Restarurant restarurant = restaurants.stream().filter(
                r-> r.getId().equals(id)).findFirst().orElse(null);

        return restarurant;
    }

}
