package zunza.myshop.response.main_view;

import lombok.Getter;
import zunza.myshop.domain.Product;

@Getter
public class TopSalesProductResponse {

	private Long id;
	private String productName;
	private Integer price;
	private String imageUrl;

	private TopSalesProductResponse(Long id, String productName, Integer price, String imageUrl) {
		this.id = id;
		this.productName = productName;
		this.price = price;
		this.imageUrl = imageUrl;
	}

	public static TopSalesProductResponse of(Product product, String thumbnailUrl) {
		return new TopSalesProductResponse(
			product.getId(),
			product.getProductName(),
			product.getPrice(),
			thumbnailUrl);
	}
}
