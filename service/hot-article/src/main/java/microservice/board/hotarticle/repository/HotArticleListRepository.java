package microservice.board.hotarticle.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HotArticleListRepository {

    private final StringRedisTemplate redisTemplate;

    // hot-article:list:{yyyyMMdd}
    private static final String KEY_FORMAT = "hot-article:list:%s";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public void add(Long articleId, LocalDateTime dateTime, Long score, Long limit, Duration ttl) {
        redisTemplate.executePipelined((RedisCallback<?>) action -> {
            StringRedisConnection connection = (StringRedisConnection) action;
            String key = generateKey(dateTime);
            connection.zAdd(key, score, String.valueOf(articleId));
            connection.zRemRange(key, 0, -limit - 1);
            connection.expire(key, ttl.toSeconds());
            return null;
        });
    }

    public List<Long> readAll(String date) {
        return redisTemplate.opsForZSet()
                .reverseRangeWithScores(generateKey(date), 0, -1).stream()
                .peek(tuple ->
                        log.info("[HotArticleListRepository.readAll] articleId: {}, score: {}", tuple.getValue(), tuple.getScore()))
                .map(ZSetOperations.TypedTuple::getValue)
                .map(Long::valueOf)
                .toList();
    }

    private String generateKey(LocalDateTime dateTime) {
        return generateKey(dateTime.format(DATE_TIME_FORMATTER));
    }

    private String generateKey(String dateTime) {
        return KEY_FORMAT.formatted(dateTime);
    }
}
