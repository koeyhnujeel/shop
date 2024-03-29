package zunza.myshop.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductOptionUpdateRequest {

	private String size;

	private String color;

	private Integer stock;

	@Builder
	public ProductOptionUpdateRequest(String size, String color, Integer stock) {
		this.size = size;
		this.color = color;
		this.stock = stock;
	}
}
