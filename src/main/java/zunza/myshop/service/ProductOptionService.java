package zunza.myshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zunza.myshop.domain.Product;
import zunza.myshop.domain.ProductOption;
import zunza.myshop.exception.ProductNotFoundException;
import zunza.myshop.repository.ProductOptionRepository;
import zunza.myshop.repository.ProductRepository;
import zunza.myshop.request.ProductOptionRequest;

@Service
@RequiredArgsConstructor
public class ProductOptionService {

	private final ProductRepository productRepository;
	private final ProductOptionRepository productOptionRepository;

	public void addProductOptions(
		Long productId,
		List<ProductOptionRequest> productOptionRequests) {

		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId));

		productOptionRequests.forEach(productOptionRequest -> productOptionRepository.save(
				ProductOption.from(productOptionRequest).setRelation(product)
			));
	}
}
