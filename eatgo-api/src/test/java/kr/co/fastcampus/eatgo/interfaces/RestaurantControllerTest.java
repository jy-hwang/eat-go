package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import kr.co.fastcampus.eatgo.domain.MenuItemRepositoryImpl;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import kr.co.fastcampus.eatgo.domain.RestaurantRepositoryImpl;
import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(LoggingTestWatcher.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired // 테스트 관련 객체에 대해서는 Spring이 특별히 허용하고 있음.
    private MockMvc mvc;

    @SpyBean(RestaurantRepositoryImpl.class)
    private RestaurantRepository restaurantRepository;

    @SpyBean(MenuItemRepositoryImpl.class)
    private MenuItemRepository menuItemRepository;

    @SpyBean(RestaurantService.class)
    private RestaurantService restaurantService;

    @Test
    @DisplayName("list test")
    public void list() throws Exception {
        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                )).andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));
    }

    @Test
    @DisplayName("detail test")
    public void detail() throws Exception {

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                )).andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                )).andExpect(content().string(
                        containsString("Kimchi")
                ));

        mvc.perform(get("/restaurants/2024"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":2024")
                )).andExpect(content().string(
                        containsString("\"name\":\"Cyber Food\"")
                ));
    }

}