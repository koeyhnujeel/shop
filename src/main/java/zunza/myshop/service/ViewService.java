package zunza.myshop.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zunza.myshop.repository.ViewRepository;
import zunza.myshop.repository.redis.CustomRedisRepository;
import zunza.myshop.response.product_detail.ViewResponse;

@Service
@RequiredArgsConstructor
public class ViewService {

	private final ViewRepository viewRepository;
	private final CustomRedisRepository redisRepository;

	public ViewResponse count(Long productId) {
		Object cachedView = redisRepository.get("view::" + productId);
		if (Objects.nonNull(cachedView)) {
			return (ViewResponse)cachedView;
		}

		Long count = viewRepository.countByProductId(productId);
		redisRepository.set("view::" + productId, count);
		redisRepository.setTimeToLive("view::" + productId, 60);

		return ViewResponse.from(count);
	}
}
