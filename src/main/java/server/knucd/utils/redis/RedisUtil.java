package server.knucd.utils.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

/**
 * Redis 서버 동작 보조 클래스
 * {@link server.knucd.config.RedisConfig#redisTemplate(RedisConnectionFactory) RedisTemplate}
 *  을 바탕으로 Redis 서버 동작
 * @see server.knucd.config.RedisConfig
 */
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Object, Object> hashOperations;


    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public void expire(String key, long seconds) {
        redisTemplate.expire(key, Duration.ofSeconds(seconds));
    }
    public void del(String key) {
        redisTemplate.delete(key);
    }

    public void hashPut(String key, Object hashKey, Object hashValue) {
        hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, hashKey, hashValue);
    }

    public Object hashGet(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public void hashDel(String key, Object hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    public Map<Object, Object> hashEntries(String key) {
        hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
