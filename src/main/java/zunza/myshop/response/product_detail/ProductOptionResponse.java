package zunza.myshop.response.product_detail;

import lombok.Getter;
import zunza.myshop.domain.ProductOption;

@Getter
public class ProductOptionResponse {
	private Long id;
	private String size;
	private String color;

	private ProductOptionResponse(Long id, String size, String color) {
		this.id = id;
		this.size = size;
		this.color = color;
	}

	public static ProductOptionResponse from(ProductOption productOption) {
		return new ProductOptionResponse(
			productOption.getId(),
			productOption.getSize(),
			productOption.getColor());
	}
}
