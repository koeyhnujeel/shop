package zunza.myshop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zunza.myshop.request.AddReviewRequest;
import zunza.myshop.request.ModifyReviewRequest;
import zunza.myshop.response.product_detail.ReviewResponse;
import zunza.myshop.service.ReviewService;

@RestController
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService productReviewService;

	@GetMapping("/products/{productId}/reviews")
	@ResponseStatus(HttpStatus.OK)
	public List<ReviewResponse> ReviewList(
		@PathVariable("productId") Long productId) {

		return productReviewService.findReviewList(productId);
	}

	@PostMapping("/user/products/{productId}/reviews")
	@ResponseStatus(HttpStatus.CREATED)
	public void ReviewAdd(
		@AuthenticationPrincipal Long userId,
		@PathVariable("productId") Long productId,
		@RequestBody AddReviewRequest req) {

		productReviewService.addReview(userId, productId, req);
	}

	@PatchMapping("/user/reviews/{reviewId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ReviewModify(
		@AuthenticationPrincipal Long userId,
		@PathVariable("reviewId") Long reviewId,
		@RequestBody ModifyReviewRequest req) {

		productReviewService.modifyReview(userId, reviewId, req);
	}

	@DeleteMapping("/user/reviews/{reviewId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ReviewRemove(
		@AuthenticationPrincipal Long userId,
		@PathVariable("reviewId") Long reviewId) {

		productReviewService.removeReview(userId, reviewId);
	}
}
