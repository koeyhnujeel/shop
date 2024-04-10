package zunza.myshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zunza.myshop.repository.ProductReviewRepository;
import zunza.myshop.response.product_detail.ProductReviewResponse;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

	private final ProductReviewRepository productReviewRepository;

	public List<ProductReviewResponse> findReviewList(Long productId) {
		return productReviewRepository.findReviewsByProductId(productId).stream()
			.map(ProductReviewResponse::from)
			.toList();
	}
}
