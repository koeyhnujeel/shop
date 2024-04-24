package zunza.myshop.response.product_management;

import lombok.Getter;

@Getter
public class ProductOptionResponseForAdmin {

	private Long id;
	private String size;
	private String color;
	private Integer stock;

	private ProductOptionResponseForAdmin(Long id, String size, String color, Integer stock) {
		this.id = id;
		this.size = size;
		this.color = color;
		this.stock = stock;
	}

	// public static ProductOptionResponseForAdmin from(ProductOption productOption) {
	// 	return new ProductOptionResponseForAdmin(
	// 		productOption.getId(),
	// 		productOption.getSize(),
	// 		productOption.getColor(),
	// 		productOption.getStock()
	// 	);
	// }
}
