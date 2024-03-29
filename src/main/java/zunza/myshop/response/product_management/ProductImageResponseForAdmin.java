package zunza.myshop.response.product_management;

import lombok.Getter;
import zunza.myshop.domain.ProductImage;

@Getter
public class ProductImageResponseForAdmin {

	private Long id;
	private String imageUrl;

	private ProductImageResponseForAdmin(Long id, String imageUrl) {
		this.id = id;
		this.imageUrl = imageUrl;
	}

	public static ProductImageResponseForAdmin from(ProductImage productImage) {
		return new ProductImageResponseForAdmin(
			productImage.getId(),
			productImage.getImageUrl());
	}
}
