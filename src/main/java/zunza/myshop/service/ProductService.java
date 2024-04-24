package zunza.myshop.service;

import java.io.IOException;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zunza.myshop.domain.Product;
import zunza.myshop.domain.Stock;
import zunza.myshop.domain.User;
import zunza.myshop.domain.View;
import zunza.myshop.exception.ProductNotFoundException;
import zunza.myshop.exception.UserNotFoundException;
import zunza.myshop.repository.ProductRepository;
import zunza.myshop.repository.StockRepository;
import zunza.myshop.repository.UserRepository;
import zunza.myshop.repository.ViewRepository;
import zunza.myshop.request.ProductRequest;
import zunza.myshop.request.ProductUpdateRequest;
import zunza.myshop.response.product_detail.ProductDetailsResponse;
import zunza.myshop.util.ImageUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final StockRepository stockRepository;
	private final UserRepository userRepository;
	private final ViewRepository viewRepository;
	private final ImageUtil imageUtil;


	public void addProduct(ProductRequest req, MultipartFile image) throws IOException {
		String savedImage = imageUtil.saveImage(image);

		Product product = Product.of(req, savedImage);
		Product savedProduct = productRepository.save(product);

		Stock stock = Stock.of(savedProduct.getId(), req.getQuantity());
		stockRepository.save(stock);
	}

	// public Map<String, Object> findProductList() {
	//
	// }

	@Transactional
	public ProductDetailsResponse findProduct(Long userId, Long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId));

		addView(userId, product);

		return ProductDetailsResponse.from(product);
	}

	// public List<ProductListResponseForAdmin> findProductListForAdmin(int page, int size, String keyword) {
	//
	// 	List<Product> productList = productRepository.findProductAndImageForAdmin(page, size, keyword);
	// 	return productList.stream()
	// 		.map(ProductListResponseForAdmin::from)
	// 		.toList();
	// }

	// public ProductDetailsForAdmin findProductForAdmin(Long productId) {
	// 	Product product = productRepository.findProductAndImageFetchJoin(productId)
	// 		.orElseThrow(() -> new ProductNotFoundException(productId));
	//
	// 	// return ProductDetailsForAdmin.of(product, images, options);
	// }

	@Transactional
	public void modifyProduct(Long productId, ProductUpdateRequest productUpdateRequest) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId));

		// product.modify(productUpdateRequest);
	}

	@Transactional
	public void removeProduct(Long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId));

		productRepository.delete(product);
	}

	private void addView(Long userId, Product product) {
		if (Objects.nonNull(userId)) {
			User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(userId));

			viewRepository.findByUserIdAndProductId(user.getId(), product.getId())
				.ifPresentOrElse(
					view -> {},
					() -> {
						View view = View.of(user, product);
						viewRepository.save(view);
					}
				);
		}
	}
}
