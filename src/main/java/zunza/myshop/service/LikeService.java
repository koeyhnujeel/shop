package zunza.myshop.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import zunza.myshop.domain.Like;
import zunza.myshop.domain.Product;
import zunza.myshop.domain.User;
import zunza.myshop.exception.LikeNotFoundException;
import zunza.myshop.exception.ProductNotFoundException;
import zunza.myshop.exception.UserNotFoundException;
import zunza.myshop.repository.LikeRepository;
import zunza.myshop.repository.ProductRepository;
import zunza.myshop.repository.UserRepository;
import zunza.myshop.repository.redis.CustomRedisRepository;
import zunza.myshop.response.UserLikeResponse;
import zunza.myshop.response.product_detail.LikeResponse;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final CustomRedisRepository redisRepository;

	public LikeResponse count(Long userId, Long productId) {
		boolean liked = isLiked(userId, productId);

		Object cachedLike = redisRepository.get("like::" + productId);
		if (Objects.nonNull(cachedLike)) {
			Long likeCount = ((Number)cachedLike).longValue();
			return LikeResponse.of(likeCount, liked);
		}

		Long count = likeRepository.countLike(productId);

		redisRepository.set("like::" + productId, count);
		redisRepository.setTimeToLive("like::" + productId, 60L);

		return LikeResponse.of(count, liked);
	}

	@Transactional
	public void like(Long userId, Long productId) {
		Like like = likeRepository.findByUserIdAndProductId(userId, productId)
			.orElseGet(() -> createNewLike(userId, productId));

		like.likeOn();
		redisRepository.delete("like::" + productId);
	}

	@Transactional
	public void cancelLike(Long userId, Long productId) {
		Like like = likeRepository.findByUserIdAndProductId(userId, productId)
			.orElseThrow(LikeNotFoundException::new);

		like.likeOff();
		redisRepository.delete("like::" + productId);
	}

	public List<UserLikeResponse> findUserLikeProducts(Long userId) {
		List<Like> userLike = likeRepository.findUserLike(userId);

		return userLike.stream()
			.map(like -> UserLikeResponse.from(like.getProduct()))
			.collect(Collectors.toList());
	}

	private boolean isLiked(Long userId, Long productId) {
		boolean liked = false;

		if (Objects.nonNull(userId)) {
			liked = likeRepository.isLiked(userId, productId);
		}
		return liked;
	}

	private Like createNewLike(Long userId, Long productId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));

		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId));
		return likeRepository.save(Like.of(user, product));
	}
}

