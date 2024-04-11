package zunza.myshop.exception;

public class ExistsNicknameException extends CustomException {

	private final static String MESSAGE = "잘못된 요청입니다.";

	public ExistsNicknameException() {
		super(MESSAGE);
		addError("nickname", "사용 중인 닉네임입니다.");
	}

	@Override
	public int getStatusCode() {
		return 409;
	}
}
