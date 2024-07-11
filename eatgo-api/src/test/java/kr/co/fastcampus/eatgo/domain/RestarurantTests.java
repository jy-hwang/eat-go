package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RestarurantTests {

    @Test
    public void creation() {
        Restarurant restarurant = new Restarurant(1004L,"Bob zip", "Busan");

        assertThat(restarurant.getId(), is(1004L));
        assertThat(restarurant.getName(), is("Bob zip"));
        assertThat(restarurant.getAddress(), is("Busan"));
    }

    @Test
    public void information() {
        Restarurant restarurant = new Restarurant(1004L, "Bob zip", "Seoul");

        assertThat(restarurant.getInformation(), is("Bob zip in Seoul"));
    }

}
