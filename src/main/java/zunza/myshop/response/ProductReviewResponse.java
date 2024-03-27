package zunza.myshop.response;

import java.time.format.DateTimeFormatter;

import lombok.Getter;
import zunza.myshop.domain.ProductReview;

@Getter
public class ProductReviewResponse {

	private Long id;
	private String nickname;
	private String content;
	private String createdAt;

	private ProductReviewResponse(Long id, String nickname, String content, String createdAt) {
		this.id = id;
		this.nickname = nickname;
		this.content = content;
		this.createdAt = createdAt;
	}

	public static ProductReviewResponse from(ProductReview productReview) {
		return new ProductReviewResponse(
			productReview.getId(),
			productReview.getUser().getNickname(),
			productReview.getContent(),
			productReview.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
		);
	}
}
