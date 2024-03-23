package zunza.myshop.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zunza.myshop.domain.ProductOption;

@Getter
@NoArgsConstructor
public class ProductOptionAddRequest {

	@NotBlank(message = "사이즈를 선택해주세요.")
	private String size;

	@NotBlank(message = "색상을 입력해주세요.")
	private String color;

	@NotNull(message = "재고량을 입력해주세요.")
	private Integer stock;

	@Builder
	public ProductOptionAddRequest(String size, String color, Integer stock) {
		this.size = size;
		this.color = color;
		this.stock = stock;
	}

	public ProductOption toEntity() {
		return ProductOption.builder()
			.size(this.size)
			.color(this.color)
			.stock(this.stock)
			.build();
	}
}
