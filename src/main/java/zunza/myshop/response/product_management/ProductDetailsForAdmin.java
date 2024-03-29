package zunza.myshop.response.product_management;

import java.util.List;

import lombok.Getter;
import zunza.myshop.constant.Category;
import zunza.myshop.domain.Product;

@Getter
public class ProductDetailsForAdmin {
	private String productName;
	private Integer price;
	private String category;
	private String description;
	private List<ProductImageResponseForAdmin> images;
	private List<ProductOptionResponseForAdmin> options;

	public ProductDetailsForAdmin(
		String productName,
		Integer price,
		String category,
		String description,
		List<ProductImageResponseForAdmin> images,
		List<ProductOptionResponseForAdmin> options) {

		this.productName = productName;
		this.price = price;
		this.category = category;
		this.description = description;
		this.images = images;
		this.options = options;
	}

	public static ProductDetailsForAdmin of(
		Product product,
		List<ProductImageResponseForAdmin> images,
		List<ProductOptionResponseForAdmin> options) {

		return new ProductDetailsForAdmin(
			product.getProductName(),
			product.getPrice(),
			product.getCategory().getName(),
			product.getDescription(),
			images,
			options
		);
	}
}
