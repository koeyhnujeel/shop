package zunza.myshop.exception;

public class UserNotFoundException extends CustomException {

	private static final String MESSAGE = "잘못된 요청입니다.";

	public UserNotFoundException(Long userId) {
		super(MESSAGE);
		addError("user ID: " + userId, "존재하지 않는 유저입니다.");
	}

	public UserNotFoundException(String email) {
		super(MESSAGE);
		addError("user EMAIL: " + email, "존재하지 않는 유저입니다.");
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}
