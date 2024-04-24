package zunza.myshop.repository.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomRedisRepositoryImpl implements CustomRedisRepository {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void setTimeToLive(String key, long timeout) {
		redisTemplate.expire(key, timeout, TimeUnit.MINUTES);
	}

	@Override
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}
}
