package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RestaurantTests {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantTests.class);

    @Test
    @DisplayName("Creation Test")
    public void creation() {
        Restaurant restaurant = new Restaurant(1004L,"Bob zip", "Busan");

        assertThat(restaurant.getId(), is(1004L));
        assertThat(restaurant.getName(), is("Bob zip"));
        assertThat(restaurant.getAddress(), is("Busan"));
    }

    @Test
    @DisplayName("Infomation Test")
    public void information() {
        Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Seoul");

        assertThat(restaurant.getInformation(), is("Bob zip in Seoul"));
    }

    @AfterEach
    void tearDown(TestInfo testInfo){
        logger.info("{} Test finished successfully.", testInfo.getDisplayName());
    }

    @AfterAll
    static void afterAll(){
        logger.info("All tests finished successfully.");
    }

}
