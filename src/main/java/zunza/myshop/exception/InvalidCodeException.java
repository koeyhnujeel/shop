package zunza.myshop.exception;

public class InvalidCodeException extends CustomException {

	private static final String MESSAGE = "잘못된 요청입니다.";

	public InvalidCodeException() {
		super(MESSAGE);
		addError("code", "인증코드를 확인해 주세요.");
	}

	@Override
	public int getStatusCode() {
		return 400;
	}
}
