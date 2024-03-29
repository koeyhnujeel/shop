package zunza.myshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import zunza.myshop.domain.Product;
import zunza.myshop.domain.ProductOption;
import zunza.myshop.exception.ProductNotFoundException;
import zunza.myshop.exception.ProductOptionNotFoundException;
import zunza.myshop.repository.ProductOptionRepository;
import zunza.myshop.repository.ProductRepository;
import zunza.myshop.request.ProductOptionRequest;
import zunza.myshop.request.ProductOptionUpdateRequest;

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

	@Transactional
	public void modifyProductOption(
		Long productOptionId,
		ProductOptionUpdateRequest productOptionUpdateRequest) {

		ProductOption productOption = productOptionRepository.findById(productOptionId)
			.orElseThrow(() -> new ProductOptionNotFoundException(productOptionId));

		productOption.update(productOptionUpdateRequest);
	}

	@Transactional
	public void removeProductOption(Long productOptionId) {
		ProductOption productOption = productOptionRepository.findById(productOptionId)
			.orElseThrow(() -> new ProductOptionNotFoundException(productOptionId));

		productOptionRepository.delete(productOption);
	}
}
