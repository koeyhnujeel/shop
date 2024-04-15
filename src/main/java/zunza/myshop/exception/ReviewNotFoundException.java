package zunza.myshop.exception;

public class ReviewNotFoundException extends CustomException {

	private static final String MESSAGE = "잘못된 요청입니다.";

	public ReviewNotFoundException(Long reviewId) {
		super(MESSAGE);
		addError("review ID: " + reviewId, "존재하지 않는 댓글입니다.");
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}
