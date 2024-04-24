package zunza.myshop.repository.redis;

public interface CustomRedisRepository {

	void set(String key, Object value);

	void setTimeToLive(String key, long timeout);

	Object get(String key);

	void delete(String key);
}
