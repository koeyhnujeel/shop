package zunza.myshop.response.product_detail;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ViewResponse {
	private Long count;

	@Builder
	private ViewResponse(Long count) {
		this.count = count;
	}

	public static ViewResponse from(Long count) {
		return ViewResponse.builder()
			.count(count)
			.build();
	}
}
