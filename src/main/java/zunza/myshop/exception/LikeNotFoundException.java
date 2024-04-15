package zunza.myshop.exception;

public class LikeNotFoundException extends CustomException {

	private static final String MESSAGE ="잘못된 요청입니다.";


	public LikeNotFoundException() {
		super(MESSAGE);
		addError("Like", "존재하지 않는 좋아요 데이터입니다.");
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}
