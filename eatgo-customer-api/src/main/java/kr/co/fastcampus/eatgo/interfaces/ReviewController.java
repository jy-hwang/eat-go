package kr.co.fastcampus.eatgo.interfaces;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/restaurants/{restaurantId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            Authentication authentication,
            @PathVariable("restaurantId") Long restaurantId,
            @Valid @RequestBody Review resource) throws URISyntaxException {
        Claims claims = (Claims) authentication.getPrincipal();
        String nickname = claims.get("nickname", String.class);
        Double score = 3.5;
        String description = "good";
        Review review = reviewService.addReview(restaurantId, nickname, score, description);
        String url = "/restaurants/" + restaurantId + "/reviews/" + review.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
