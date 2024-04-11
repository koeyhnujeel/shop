package zunza.myshop.exception;

public class ExistsEmailException extends CustomException {

	private final static String MESSAGE = "잘못된 요청입니다.";

	public ExistsEmailException() {
		super(MESSAGE);
		addError("email", "사용 중인 이메일입니다.");
	}

	@Override
	public int getStatusCode() {
		return 409;
	}
}
