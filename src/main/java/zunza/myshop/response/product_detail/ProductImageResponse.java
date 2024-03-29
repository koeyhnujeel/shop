package zunza.myshop.response.product_detail;

import lombok.Getter;

@Getter
public class ProductImageResponse {

	private String imageUrl;

	private ProductImageResponse(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public static ProductImageResponse from(String imageUrl) {
		return new ProductImageResponse(imageUrl);
	}
}
