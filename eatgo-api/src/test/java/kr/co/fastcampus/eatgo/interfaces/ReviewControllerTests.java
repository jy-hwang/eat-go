package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReviewService;
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
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(LoggingTestWatcher.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    @DisplayName("리뷰작성")
    public void create() throws Exception {
        mvc.perform(post("/restaurants/1/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"jackie\",\"score\":3.5,\"description\":\"good\"}")
                )
                .andExpect(status().isCreated());
        verify(reviewService).addReview(any());
    }

}
