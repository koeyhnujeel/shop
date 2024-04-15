package zunza.myshop.exception;

public class PermissionDeniedException extends CustomException {

	private static final String MESSAGE = "잘못된 요청입니다.";

	public PermissionDeniedException() {
		super(MESSAGE);
		addError("error", "해당 요청에 대한 권한이 없습니다.");
	}

	@Override
	public int getStatusCode() {
		return 403;
	}
}
