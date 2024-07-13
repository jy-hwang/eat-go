package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(LoggingTestWatcher.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    @DisplayName("유효한 속성값을 가진 리뷰 작성")
    public void createWithValidAttributes() throws Exception {
        given(reviewService.addReview(any())).willReturn(
                Review.builder()
                        .id(123L)
                        .name("jackie")
                        .score(3.5)
                        .description("good")
                        .build()
        );

        mvc.perform(post("/restaurants/1/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"jackie\",\"score\":3.5,\"description\":\"good\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1/review/123"));
        verify(reviewService).addReview(any());
    }

    @Test
    @DisplayName("유효하지 않은 속성값을 가진 리뷰 작성")
    public void createWithInvalidAttributes() throws Exception {
        mvc.perform(post("/restaurants/1/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
        verify(reviewService, never()).addReview(any());
    }

}
