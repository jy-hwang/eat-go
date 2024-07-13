package kr.co.fastcampus.eatgo.interfaces;

import jakarta.validation.Valid;
import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/restaurants/{restaurantId}/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("restaurantId") Long restaurantId,
            @Valid @RequestBody Review resource) throws URISyntaxException {

        Review review = reviewService.addReview(resource);

        String url = "/restaurants/" + restaurantId + "/review/" + review.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
