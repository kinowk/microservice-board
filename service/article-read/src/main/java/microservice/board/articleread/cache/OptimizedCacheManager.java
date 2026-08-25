package microservice.board.articleread.cache;

import lombok.RequiredArgsConstructor;
import microservice.board.common.dataserializer.DataSerializer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

@Component
@RequiredArgsConstructor
public class OptimizedCacheManager {

    private final StringRedisTemplate redisTemplate;
    private final OptimizedCacheLockProvider optimizedCacheLockProvider;

    private static final String DELIMITER = "::";

    public Object process(String type, long ttlSeconds, Object[] args, Class<?> returnType,
                          OptimizedCacheOriginDataSupplier<?> originDataSupplier) throws Throwable {
        String key = generateKey(type, args);

        String cachedData = redisTemplate.opsForValue().get(key);
        if (cachedData == null) { // Cache된 Data가 없는 경우 원본 데이터를 조회하고 Cache 한다.
            return refresh(originDataSupplier, key, ttlSeconds);
        }

        // Cache된 데이터가 있는 경우
        OptimizedCache optimizedCache = DataSerializer.deserialize(cachedData, OptimizedCache.class);
        if (optimizedCache == null) { // 역직렬화 실패 시 재요청
            return refresh(originDataSupplier, key, ttlSeconds);
        }

        if (!optimizedCache.isExpired()) { // 데이터가 만료되지 않은 경우
            return optimizedCache.parseData(returnType);
        }

        // 데이터가 만료된 경우
        if (!optimizedCacheLockProvider.lock(key)) { // lock 획득 실패 시 데이터를 반환한다.
            return optimizedCache.parseData(returnType);
        }

        // lock 획득 시 데이텉 갱신
        try {
            return refresh(originDataSupplier, key, ttlSeconds);
        } finally {
            optimizedCacheLockProvider.unlock(key);
        }
    }

    private Object refresh(OptimizedCacheOriginDataSupplier<?> originDataSupplier, String key, long ttlSeconds) throws Throwable {
        Object result = originDataSupplier.get();

        OptimizedCacheTTL optimizedCacheTTL = OptimizedCacheTTL.of(ttlSeconds);
        OptimizedCache optimizedCache = OptimizedCache.of(result, optimizedCacheTTL.getLogicalTTL());

        redisTemplate.opsForValue()
                .set(
                        key,
                        DataSerializer.serialize(optimizedCache),
                        optimizedCacheTTL.getPhysicalTTL()
                );

        return result;
    }

    private String generateKey(String prefix, Object[] args) {
        return prefix + DELIMITER +
                Arrays.stream(args)
                        .map(String::valueOf)
                        .collect(joining(DELIMITER));
    }
}
