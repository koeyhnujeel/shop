package zunza.myshop.response.product_detail;

import java.time.format.DateTimeFormatter;

import lombok.Getter;
import zunza.myshop.domain.Review;

@Getter
public class ReviewResponse {

	private Long id;
	private Long userId;
	private String nickname;
	private String content;
	private String createdAt;

	private ReviewResponse(Long reviewId, Long userId, String nickname, String content, String createdAt) {
		this.id = reviewId;
		this.userId = userId;
		this.nickname = nickname;
		this.content = content;
		this.createdAt = createdAt;
	}

	public static ReviewResponse from(Review productReview) {
		return new ReviewResponse(
			productReview.getId(),
			productReview.getUser().getId(),
			productReview.getUser().getNickname(),
			productReview.getContent(),
			productReview.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
		);
	}
}
