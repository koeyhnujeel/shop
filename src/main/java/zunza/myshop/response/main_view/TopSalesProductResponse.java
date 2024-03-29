package zunza.myshop.response.main_view;

import lombok.Getter;

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

	public static TopSalesProductResponse of(Long id, String productName, Integer price, String imageUrl) {
		return new TopSalesProductResponse(id, productName, price, imageUrl);
	}
}
