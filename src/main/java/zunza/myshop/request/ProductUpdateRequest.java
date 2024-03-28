package zunza.myshop.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zunza.myshop.constant.Category;

@Getter
@NoArgsConstructor
public class ProductUpdateRequest {

	@Length(min = 1, max = 40, message = "상품명은 1 ~ 40자까지 가능합니다.")
	private String productName;

	@Min(value = 50, message = "최소 가격은 50원 입니다.")
	private Integer price;

	private Category category;

	@Length(min = 10, max = 1000, message = "상품 설명은 10 ~ 1000자까지 가능합니다.")
	private String description;

	@Builder
	public ProductUpdateRequest(
		String productName,
		Integer price,
		Category category,
		String description) {

		this.productName = productName;
		this.price = price;
		this.category = category;
		this.description = description;
	}
}
