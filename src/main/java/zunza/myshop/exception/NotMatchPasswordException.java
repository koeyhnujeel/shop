package zunza.myshop.exception;

public class NotMatchPasswordException extends CustomException{

	private static final String MESSAGE = "잘못된 요청입니다.";

	public NotMatchPasswordException() {
		super(MESSAGE);
		addError("password", "현재 비밀번호를 확인해 주세요.");
	}

	@Override
	public int getStatusCode() {
		return 401;
	}
}
