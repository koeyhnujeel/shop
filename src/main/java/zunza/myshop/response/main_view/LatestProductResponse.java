package zunza.myshop.response.main_view;

import lombok.Getter;

@Getter
public class LatestProductResponse {

	private Long id;
	private String productName;
	private Integer price;
	private String imageUrl;

	private LatestProductResponse(Long id, String productName, Integer price, String imageUrl) {
		this.id = id;
		this.productName = productName;
		this.price = price;
		this.imageUrl = imageUrl;
	}

	public static LatestProductResponse of(Long id, String productName, Integer price, String imageUrl) {
		return new LatestProductResponse(id, productName, price, imageUrl);
	}
}
