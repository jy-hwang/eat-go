package kr.co.fastcampus.eatgo.domain;

import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(LoggingTestWatcher.class)
public class CategoryTests {

    @Test
    @DisplayName("Category Creation Test")
    public void creation() {
        Category category = Category.builder().name("Korean Food").build();

        assertThat(category.getName(), is("Korean Food"));
    }

}
