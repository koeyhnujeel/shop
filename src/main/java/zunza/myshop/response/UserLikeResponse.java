package zunza.myshop.response;

import lombok.Builder;
import lombok.Getter;
import zunza.myshop.domain.Product;

@Getter
public class UserLikeResponse {
	private Long productId;
	private String productName;
	private Integer price;
	private String thumbnailUrl;

	private UserLikeResponse(
		Long productId,
		String productName,
		Integer price,
		String thumbnailUrl) {

		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.thumbnailUrl = thumbnailUrl;
	}

	public static UserLikeResponse from(Product product) {
		return new UserLikeResponse(
			product.getId(),
			product.getProductName(),
			product.getPrice(),
			product.getImage()
		);
	}
}
