package zunza.myshop.response.product_detail;

import lombok.Getter;

@Getter
public class LikeResponse {
	private Long count;
	private boolean userLiked;

	private LikeResponse(Long count, boolean Liked) {
		this.count = count;
		this.userLiked = Liked;
	}

	public static LikeResponse of(Long count, boolean liked) {
		return new LikeResponse(count, liked);
	}
}
