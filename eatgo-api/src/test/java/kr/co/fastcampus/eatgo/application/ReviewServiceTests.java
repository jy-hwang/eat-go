package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.domain.ReviewRepository;
import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(LoggingTestWatcher.class)
@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepository;

    //@InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    public void setUp(){
        reviewService = new ReviewService(reviewRepository);
    }

    @Test
    @DisplayName("리뷰 작성 테스트")
    public void addReview() {
        Review review
                = Review.builder()
                .name("jyhwang")
                .score(4.0)
                .description("goood")
                .build();

        reviewService.addReview(review);

        verify(reviewRepository).save(any());
    }

}
