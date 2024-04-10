package zunza.myshop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zunza.myshop.response.product_detail.ProductReviewResponse;
import zunza.myshop.service.ProductReviewService;

@RestController
@RequiredArgsConstructor
public class ProductReviewController {

	private final ProductReviewService productReviewService;

	@GetMapping("/products/{productId}/reviews")
	@ResponseStatus(HttpStatus.OK)
	public List<ProductReviewResponse> ReviewList(@PathVariable("productId") Long productId) {

		return productReviewService.findReviewList(productId);
	}
}
