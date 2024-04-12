package zunza.myshop.exception;

public class ExpiredCodeException extends CustomException {

	private static final String MESSAGE = "잘못된 요청입니다.";

	public ExpiredCodeException() {
		super(MESSAGE);
		addError("code", "유효하지 않은 인증코드입니다.");
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}
