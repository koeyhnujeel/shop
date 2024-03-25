package zunza.myshop.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductOptionRequest {

	@NotBlank(message = "사이즈를 선택해주세요.")
	private String size;

	@NotBlank(message = "색상을 입력해주세요.")
	private String color;

	@NotNull(message = "재고량을 입력해주세요.")
	private Integer stock;

	@Builder
	public ProductOptionRequest(String size, String color, Integer stock) {
		this.size = size;
		this.color = color;
		this.stock = stock;
	}
}
