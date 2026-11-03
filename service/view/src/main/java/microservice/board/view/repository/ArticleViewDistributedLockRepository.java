package microservice.board.view.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class ArticleViewDistributedLockRepository {

    // view:article:{article_id}:user:{user_id}:count
    private static final String KEY_FORMAT = "view:article:%s:user:%s:count";

    private final StringRedisTemplate redisTemplate;

    public boolean lock(Long articleId, Long userId, Duration ttl) {
        return Boolean.TRUE.equals(
                redisTemplate.opsForValue().setIfAbsent(
                        generateKey(articleId, userId),
                        "",
                        ttl
                )
        );
    }

    private String generateKey(Long articleId, Long userId) {
        return KEY_FORMAT.formatted(articleId, userId);
    }
}
