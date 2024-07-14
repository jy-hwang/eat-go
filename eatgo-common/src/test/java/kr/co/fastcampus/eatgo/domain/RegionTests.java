package kr.co.fastcampus.eatgo.domain;

import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(LoggingTestWatcher.class)
public class RegionTests {

    @Test
    @DisplayName("Region Creation Test")
    public void creation() {
        Region region = Region.builder().name("서울").build();

        assertThat(region.getName(), is("서울"));
    }

}
