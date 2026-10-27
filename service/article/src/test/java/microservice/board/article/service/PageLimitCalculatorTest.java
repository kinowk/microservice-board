package microservice.board.article.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PageLimitCalculatorTest {

    @Test
    void calculatePageLimit() {
        assertThat(calculatePageLimit(1L, 20L, 10L)).isEqualTo(201L);
        assertThat(calculatePageLimit(10L, 10L, 10L)).isEqualTo(101L);
        assertThat(calculatePageLimit(101L, 20L, 10L)).isEqualTo(2201L);
    }

    Long calculatePageLimit(Long page, Long pageSize, Long movablePageCount) {
        return PageLimitCalculator.calculatePageLimit(page, pageSize, movablePageCount);
    }
}