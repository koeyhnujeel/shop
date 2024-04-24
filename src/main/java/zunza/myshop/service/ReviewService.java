package zunza.myshop.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zunza.myshop.domain.Product;
import zunza.myshop.domain.Review;
import zunza.myshop.domain.User;
import zunza.myshop.exception.PermissionDeniedException;
import zunza.myshop.exception.ProductNotFoundException;
import zunza.myshop.exception.ReviewNotFoundException;
import zunza.myshop.exception.UserNotFoundException;
import zunza.myshop.repository.ProductRepository;
import zunza.myshop.repository.ReviewRepository;
import zunza.myshop.repository.UserRepository;
import zunza.myshop.repository.redis.CustomRedisRepository;
import zunza.myshop.request.AddReviewRequest;
import zunza.myshop.request.ModifyReviewRequest;
import zunza.myshop.response.product_detail.ReviewResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

	private final CustomRedisRepository redisRepository;
	private final ReviewRepository reviewRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	public List<ReviewResponse> findReviewList(Long productId) {
		Object cachedReview = redisRepository.get("review::" + productId);
		if (Objects.nonNull(cachedReview)) {
			return (List<ReviewResponse>)cachedReview;
		}

		List<ReviewResponse> reviews = reviewRepository.findReviewsByProductId(productId).stream()
			.map(ReviewResponse::from)
			.toList();

		redisRepository.set("review::" + productId, reviews);
		redisRepository.setTimeToLive("review::" + productId, 60L);

		return reviews;
	}

	@Transactional
	public void addReview(Long userId, Long productId, AddReviewRequest req) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));

		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId));

		Review productReview = Review.of(user, product, req);
		reviewRepository.save(productReview);
		redisRepository.delete("review::" + productId);
	}

	@Transactional
	public void modifyReview(Long userId, Long reviewId, ModifyReviewRequest req) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewNotFoundException(reviewId));

		checkWriter(userId, review);

		review.updateReview(req.getContent());
		redisRepository.delete("review::" + review.getProduct().getId());
	}

	@Transactional
	public void removeReview(Long userId, Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewNotFoundException(reviewId));

		checkWriter(userId, review);

		reviewRepository.delete(review);
		redisRepository.delete("review::" + review.getProduct().getId());
	}

	private void checkWriter(Long userId, Review review) {
		if (!Objects.equals(review.getUser().getId(), userId)) {
			throw new PermissionDeniedException();
		}
	}
}
