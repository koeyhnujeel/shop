package zunza.myshop.response.product_detail;

import java.util.List;

import lombok.Getter;
import zunza.myshop.domain.Product;

@Getter
public class ProductDetails {

	private String productName;
	private Integer price;
	private String description;
	private Integer likeCount;
	private List<ProductOptionResponse> productOptionResponses;
	private List<ProductImageResponse> productImageResponses;

	private ProductDetails(
		String productName,
		Integer price,
		String description,
		Integer likeCount,
		List<ProductOptionResponse> productOptionResponses,
		List<ProductImageResponse> productImageResponses
	) {

		this.productName = productName;
		this.price = price;
		this.description = description;
		this.likeCount = likeCount;
		this.productOptionResponses = productOptionResponses;
		this.productImageResponses = productImageResponses;
	}

	public static ProductDetails of(
		Product product,
		List<ProductOptionResponse> productOptionResponses,
		List<ProductImageResponse>  productImageResponses
	) {

		return new ProductDetails(
			product.getProductName(),
			product.getPrice(),
			product.getDescription(),
			product.getLikeCount(),
			productOptionResponses,
			productImageResponses);
	}
}
