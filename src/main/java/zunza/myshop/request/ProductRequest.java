package zunza.myshop.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zunza.myshop.constant.Category;

@Getter
@NoArgsConstructor
public class ProductRequest {

	@NotBlank(message = "상품명을 입력해주세요.")
	private String productName;

	@Min(value = 50, message = "최소 가격은 50원 입니다.")
	@NotNull(message = "상품 가격을 입력해주세요.")
	private Integer price;

	@NotNull(message = "카테고리를 선택해주세요.")
	private Category category;

	@Min(value = 10, message = "최소 수량은 10갸 입니다.")
	@NotNull(message = "상품 재고량을 입력해주세요.")
	private Integer quantity;
}
