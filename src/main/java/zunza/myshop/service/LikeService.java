package zunza.myshop.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import zunza.myshop.constant.LikeStatus;
import zunza.myshop.domain.Like;
import zunza.myshop.domain.Product;
import zunza.myshop.domain.User;
import zunza.myshop.exception.LikeNotFoundException;
import zunza.myshop.exception.ProductNotFoundException;
import zunza.myshop.exception.UserNotFoundException;
import zunza.myshop.repository.LikeRepository;
import zunza.myshop.repository.ProductRepository;
import zunza.myshop.repository.UserRepository;
import zunza.myshop.response.product_detail.LikeResponse;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

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

	private Like createNewLike(Long userId, Long productId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));

		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId));
		return likeRepository.save(Like.of(user, product));
	}

}
