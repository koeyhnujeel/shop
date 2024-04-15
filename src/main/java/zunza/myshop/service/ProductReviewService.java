package zunza.myshop.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import zunza.myshop.domain.Product;
import zunza.myshop.domain.ProductReview;
import zunza.myshop.domain.User;
import zunza.myshop.exception.PermissionDeniedException;
import zunza.myshop.exception.ProductNotFoundException;
import zunza.myshop.exception.ReviewNotFoundException;
import zunza.myshop.exception.UserNotFoundException;
import zunza.myshop.repository.ProductRepository;
import zunza.myshop.repository.ProductReviewRepository;
import zunza.myshop.repository.UserRepository;
import zunza.myshop.request.AddReviewRequest;
import zunza.myshop.request.ModifyReviewRequest;
import zunza.myshop.response.product_detail.ProductReviewResponse;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

	private final ProductReviewRepository productReviewRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	public List<ProductReviewResponse> findReviewList(Long productId) {
		return productReviewRepository.findReviewsByProductId(productId).stream()
			.map(ProductReviewResponse::from)
			.toList();
	}

	public void addReview(Long userId, Long productId, AddReviewRequest req) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));

		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId));

		ProductReview productReview = ProductReview.of(user, product, req);
		productReviewRepository.save(productReview);
	}

	@Transactional
	public void modifyReview(Long userId, Long reviewId, ModifyReviewRequest req) {
		ProductReview review = productReviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewNotFoundException(reviewId));

		checkWriter(userId, review);

		review.updateReview(req.getContent());
	}

	@Transactional
	public void removeReview(Long userId, Long reviewId) {
		ProductReview review = productReviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewNotFoundException(reviewId));

		checkWriter(userId, review);

		productReviewRepository.delete(review);
	}

	private void checkWriter(Long userId, ProductReview review) {
		if (!Objects.equals(review.getUser().getId(), userId)) {
			throw new PermissionDeniedException();
		}
	}
}
