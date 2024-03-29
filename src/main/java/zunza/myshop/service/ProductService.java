package zunza.myshop.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zunza.myshop.domain.Product;
import zunza.myshop.domain.ProductImage;
import zunza.myshop.domain.ProductOption;
import zunza.myshop.domain.ProductReview;
import zunza.myshop.exception.ProductNotFoundException;
import zunza.myshop.repository.ProductImageRepository;
import zunza.myshop.repository.ProductOptionRepository;
import zunza.myshop.repository.ProductRepository;
import zunza.myshop.repository.ProductReviewRepository;
import zunza.myshop.request.ProductRequest;
import zunza.myshop.request.ProductOptionRequest;
import zunza.myshop.request.ProductUpdateRequest;
import zunza.myshop.response.main_view.LatestProductResponse;
import zunza.myshop.response.product_detail.ProductDetails;
import zunza.myshop.response.product_detail.ProductImageResponse;
import zunza.myshop.response.product_management.ProductDetailsForAdmin;
import zunza.myshop.response.product_management.ProductImageResponseForAdmin;
import zunza.myshop.response.product_management.ProductListResponseForAdmin;
import zunza.myshop.response.product_detail.ProductOptionResponse;
import zunza.myshop.response.product_detail.ProductReviewResponse;
import zunza.myshop.response.main_view.TopSalesProductResponse;
import zunza.myshop.response.product_management.ProductOptionResponseForAdmin;
import zunza.myshop.util.ImageUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductOptionRepository productOptionRepository;
	private final ProductImageRepository productImageRepository;
	private final ProductReviewRepository productReviewRepository;
	private final ImageUtil imageUtil;


	public void addProduct(
		ProductRequest productRequest,
		List<ProductOptionRequest> productOptionsRequest,
		MultipartFile mainImage,
		List<MultipartFile> images
		) throws IOException {

		Product product = Product.from(productRequest);
		productRepository.save(product);

		productOptionsRequest.forEach(productOptionRequest ->
			productOptionRepository.save(ProductOption.from(productOptionRequest).setRelation(product)));

		List<ProductImage> productImages = imageUtil.convertToEntitiesWithResizeAndSave(mainImage, images);
		productImages.forEach(productImage -> productImageRepository.save(productImage.setRelation(product)));
	}

	public Map<String, Object> findProductList() {

		List<Product> topSalesProductsWithImages = productRepository.findProductsAndImageByCriteria("sales");
		List<TopSalesProductResponse> topSalesProducts = topSalesProductsWithImages.stream()
			.map(product -> TopSalesProductResponse.of(
				product.getId(),
				product.getProductName(),
				product.getPrice(),
				getThumbnailUrl(product)))
			.toList();

		List<Product> latestProductsWithImages = productRepository.findProductsAndImageByCriteria("latest");
		List<LatestProductResponse> latestProducts = latestProductsWithImages.stream()
			.map(product -> LatestProductResponse.of(
				product.getId(),
				product.getProductName(),
				product.getPrice(),
				getThumbnailUrl(product)))
			.toList();

		Map<String, Object> mainViewResponse = new HashMap<>();
		mainViewResponse.put("topSalesProducts", topSalesProducts);
		mainViewResponse.put("latestProducts", latestProducts);

		return mainViewResponse;
	}

	private String getThumbnailUrl(Product product) {
		return String.valueOf(product.getImages().stream()
			.filter(productImage -> productImage.getImageName().startsWith("thumbnail"))
			.findFirst()
			.map(ProductImage::getImageUrl)
			.orElse("")
		);
	}

	public ProductDetails findProduct(Long productId) {
		Product product = productRepository.findProductAndImageFetchJoin(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId));

		List<ProductOptionResponse> options = product.getOptions().stream()
			.map(ProductOptionResponse::from)
			.toList();

		List<ProductImageResponse> images = product.getImages().stream()
			.filter(productImage -> !productImage.getImageName().startsWith("thumbnail"))
			.map(productImage -> ProductImageResponse.from(productImage.getImageUrl()))
			.toList();

		List<ProductReview> productReviews = productReviewRepository.findReviewsAndUserByProductId(productId);
		List<ProductReviewResponse> reviews = productReviews.stream()
			.map(ProductReviewResponse::from)
			.toList();

		return ProductDetails.of(product, options, images, reviews);
	}

	public List<ProductListResponseForAdmin> findProductListForAdmin(int page, int size, String keyword) {

		List<Product> productList = productRepository.findProductAndImageForAdmin(page, size, keyword);
		return productList.stream()
			.map(ProductListResponseForAdmin::from)
			.toList();
	}

	public ProductDetailsForAdmin findProductForAdmin(Long productId) {
		Product product = productRepository.findProductAndImageFetchJoin(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId));

		List<ProductImageResponseForAdmin> images = product.getImages().stream()
			.map(ProductImageResponseForAdmin::from)
			.toList();

		List<ProductOptionResponseForAdmin> options = product.getOptions().stream()
			.map(ProductOptionResponseForAdmin::from)
			.toList();

		return ProductDetailsForAdmin.of(product, images, options);
	}

	@Transactional
	public void productModify(Long productId, ProductUpdateRequest productUpdateRequest) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId));

		product.modify(productUpdateRequest);
	}
}
