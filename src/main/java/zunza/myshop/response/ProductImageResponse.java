package zunza.myshop.response;

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
