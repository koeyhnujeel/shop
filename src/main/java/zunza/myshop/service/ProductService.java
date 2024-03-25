package zunza.myshop.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import zunza.myshop.domain.Product;
import zunza.myshop.domain.ProductImage;
import zunza.myshop.domain.ProductOption;
import zunza.myshop.repository.ProductImageRepository;
import zunza.myshop.repository.ProductOptionRepository;
import zunza.myshop.repository.ProductRepository;
import zunza.myshop.request.ProductRequest;
import zunza.myshop.request.ProductOptionRequest;
import zunza.myshop.response.LatestProductResponse;
import zunza.myshop.response.TopSalesProductResponse;
import zunza.myshop.util.ImageUtil;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductOptionRepository productOptionRepository;
	private final ProductImageRepository productImageRepository;
	private final ImageUtil imageUtil;


	public void addProduct(
		ProductRequest productRequest,
		ProductOptionRequest productOptionARequest,
		MultipartFile mainImage,
		List<MultipartFile> images
		) throws IOException {

		Product product = Product.from(productRequest);
		productRepository.save(product);

		ProductOption productOption = ProductOption.from(productOptionARequest);
		productOption.setRelation(product);
		productOptionRepository.save(productOption);

		List<ProductImage> productImages = imageUtil.convertToEntitiesWithResizeAndSave(mainImage, images);
		productImages.forEach(productImage -> productImageRepository.save(productImage.setRelation(product)));
	}

	public Map<String, Object> findProductList() {

		List<Product> topSalesProductsWithImages = productRepository.findMainViewProductsWithImage("sales");
		List<TopSalesProductResponse> topSalesProducts = topSalesProductsWithImages.stream()
			.map(product -> TopSalesProductResponse.of(
				product.getId(),
				product.getProductName(),
				product.getPrice(),
				getThumbnailUrl(product)))
			.toList();

		List<Product> latestProductsWithImages = productRepository.findMainViewProductsWithImage("latest");
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
}
