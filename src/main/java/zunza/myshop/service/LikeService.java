package zunza.myshop.service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
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
import zunza.myshop.response.UserLikeResponse;
import zunza.myshop.response.product_detail.LikeResponse;
import zunza.myshop.util.ImageUtil;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final ImageUtil imageUtil;

	public LikeResponse count(Long userId, Long productId) {

		Long count = likeRepository.countLike(productId);
		boolean liked = false;

		if (Objects.nonNull(userId)) {
			liked = likeRepository.isLiked(userId, productId);
		}

		return LikeResponse.of(count, liked);
	}

	@Transactional
	public void like(Long userId, Long productId) {
		Like like = likeRepository.findByUserIdAndProductId(userId, productId)
			.orElseGet(() -> createNewLike(userId, productId));

		like.likeOn();
	}

	@Transactional
	public void cancelLike(Long userId, Long productId) {
		Like like = likeRepository.findByUserIdAndProductId(userId, productId)
			.orElseThrow(LikeNotFoundException::new);

		like.likeOff();
	}

	private Like createNewLike(Long userId, Long productId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));

		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId));
		return likeRepository.save(Like.of(user, product));
	}

	public List<UserLikeResponse> findUserLikeProducts(Long userId) {
		List<Like> userLike = likeRepository.findUserLike(userId);

		return userLike.stream()
			.map(like -> UserLikeResponse.from(like.getProduct()))
			.collect(Collectors.toList());
	}
}

