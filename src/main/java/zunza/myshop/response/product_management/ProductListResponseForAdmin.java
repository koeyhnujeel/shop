package zunza.myshop.response.product_management;

import lombok.Getter;
import zunza.myshop.domain.Product;
import zunza.myshop.domain.ProductImage;

@Getter
public class ProductListResponseForAdmin {

	private Long id;
	private String productName;
	private String thumbnailUrl;

	private ProductListResponseForAdmin(
		Long id,
		String productName,
		String thumbnailUrl) {

		this.id = id;
		this.productName = productName;
		this.thumbnailUrl = thumbnailUrl;
	}

	public static ProductListResponseForAdmin from(Product product) {
		return new ProductListResponseForAdmin(
			product.getId(),
			product.getProductName(),
			product.getImages().stream()
				.filter(productImage -> productImage.getImageName().startsWith("thumbnail"))
				.findFirst()
				.map(ProductImage::getImageUrl)
				.orElse(""));
	}
}
