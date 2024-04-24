package zunza.myshop.response.product_detail;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import zunza.myshop.domain.Product;

@Getter
public class ProductDetailsResponse {

	private String productName;
	private String image;
	private Integer price;

	@Builder
	private ProductDetailsResponse(
		String productName,
		String image,
		Integer price) {

		this.productName = productName;
		this.image = image;
		this.price = price;
	}

	public static ProductDetailsResponse from(Product product) {
		return ProductDetailsResponse.builder()
			.productName(product.getProductName())
			.image(product.getImage())
			.price(product.getPrice())
			.build();
	}
}
