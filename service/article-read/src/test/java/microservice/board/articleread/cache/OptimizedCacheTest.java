package microservice.board.articleread.cache;

import lombok.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.*;

class OptimizedCacheTest {

    @Test
    void parseDataTest() {
        parseDataTest("data", 1L);
        parseDataTest(3L, 1L);
        parseDataTest(3, 1L);
        parseDataTest(new TestData("hihi"), 1L);
    }

    void parseDataTest(Object data, long ttlSeconds) {
        // given
        OptimizedCache optimizedCache = OptimizedCache.of(data, Duration.ofSeconds(ttlSeconds));
        System.out.println("optimizedCache = " + optimizedCache);

        // when
        Object resolvedData = optimizedCache.parseData(data.getClass());

        // then
        System.out.println("resolvedData = " + resolvedData);
        assertThat(resolvedData).isEqualTo(data);
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestData {
        private String data;
    }
}